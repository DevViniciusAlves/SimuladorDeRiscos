// ============================================
// SIMULADOR DE RISCOS - FRONTEND
// ============================================

const API_BASE = 'http://localhost:8080/api';
let currentUser = null;
let currentAvaliacaoId = null;
let currentRespostas = {};

// ============================================
// FUNÇÕES DE NAVEGAÇÃO
// ============================================

function showSection(sectionId) {
    // Verifica se o usuário precisa estar logado
    const protectedSections = ['dashboard', 'avaliacao-details', 'simulacao', 'nova-simulacao'];

    if (protectedSections.includes(sectionId) && !currentUser) {
        showModal('Acesso Negado', 'Você precisa estar logado para acessar esta seção.', 'error');
        showSection('login');
        return;
    }

    // Oculta todas as seções
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });

    // Mostra a seção selecionada
    document.getElementById(sectionId).classList.add('active');

    // Carrega dados dinamicamente baseado na seção
    if (sectionId === 'dashboard') {
        loadAvaliacoes();
        loadPerguntas();
    }
}

function switchTab(tabId, button) {
    // Oculta todos os tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });

    // Remove ativo de todos os botões
    document.querySelectorAll('.tab-button').forEach(btn => {
        btn.classList.remove('active');
    });

    // Mostra o tab selecionado
    document.getElementById(tabId).classList.add('active');

    // Marca o botão como ativo
    if (button) {
        button.classList.add('active');
    }

    // Carrega dados se necessário
    if (tabId === 'nova-avaliacao') {
        loadPerguntas();
    } else if (tabId === 'tipos-risco') {
        loadTiposRisco();
    }
}

// ============================================
// FUNÇÕES DE AUTENTICAÇÃO
// ============================================

async function registerUser(event) {
    event.preventDefault();

    const name = document.getElementById('register-name').value.trim();
    const email = document.getElementById('register-email').value.trim();
    const cpf = document.getElementById('register-cpf').value.replace(/\D/g, '');
    const password = document.getElementById('register-password').value;

    // Validações com mensagens por campo
    if (!name) { showModal('Erro', 'O campo Nome é obrigatório.', 'error'); return; }
    if (!email) { showModal('Erro', 'O campo Email é obrigatório.', 'error'); return; }
    if (!cpf) { showModal('Erro', 'O campo CPF é obrigatório.', 'error'); return; }
    if (!password) { showModal('Erro', 'O campo Senha é obrigatório.', 'error'); return; }

    if (cpf.length !== 11) {
        showModal('Erro', 'CPF deve ter 11 dígitos.', 'error');
        return;
    }

    if (password.length < 6) {
        showModal('Erro', 'Senha deve ter ao menos 6 caracteres.', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/usuarios/cadastro`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nome: name,
                email: email,
                cpf: cpf,
                senha: password
            })
        });

        if (response.ok) {
            const user = await response.json();
            showModal('Sucesso', 'Cadastro realizado com sucesso! Agora faça login.', 'success');
            document.getElementById('register-form').reset();
            setTimeout(() => showSection('login'), 2000);
        } else {
            // tentar extrair mensagem do backend
            let msg = 'Falha ao cadastrar. Verifique os dados e tente novamente.';
            try {
                const err = await response.json();
                if (err && err.error) msg = err.error;
            } catch (e) {}
            showModal('Erro', msg, 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        showModal('Erro', 'Erro ao conectar com o servidor.', 'error');
    }
}

async function loginUser(event) {
    event.preventDefault();

    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    if (!email || !password) {
        showModal('Erro', 'Email e senha são obrigatórios.', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/usuarios/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email, senha: password })
        });

        if (response.ok) {
            const user = await response.json();
            currentUser = user;
            localStorage.setItem('currentUser', JSON.stringify(user));

            updateNavbar();
            document.getElementById('login-form').reset();
            showModal('Sucesso', `Bem-vindo, ${user.nome}!`, 'success');

            // Atualiza nome no dashboard
            document.getElementById('user-name').textContent = user.nome || '';
            document.getElementById('user-email').textContent = user.email || '';

            setTimeout(() => showSection('dashboard'), 1000);
        } else {
            let msg = 'Credenciais inválidas.';
            if (response.status === 401) msg = 'Senha incorreta.';
            if (response.status === 400) {
                try { const err = await response.json(); if (err && err.error) msg = err.error; } catch(e){}
            }
            showModal('Erro', msg, 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        showModal('Erro', 'Erro ao conectar com o servidor.', 'error');
    }
}

function logout() {
    currentUser = null;
    localStorage.removeItem('currentUser');
    currentAvaliacaoId = null;
    updateNavbar();
    showSection('home');
    showModal('Sucesso', 'Você saiu da sua conta.', 'success');
}

function updateNavbar() {
    const dashboardLink = document.getElementById('dashboard-link');
    const logoutLink = document.getElementById('logout-link');

    if (currentUser) {
        dashboardLink.style.display = 'block';
        logoutLink.style.display = 'block';
        // mostra nome no header
        const nameElem = document.getElementById('user-name');
        if (nameElem) nameElem.textContent = currentUser.nome || '';
        const emailElem = document.getElementById('user-email');
        if (emailElem) emailElem.textContent = currentUser.email || '';
    } else {
        dashboardLink.style.display = 'none';
        logoutLink.style.display = 'none';
    }
}

// ============================================
// FUNÇÕES DE AVALIAÇÕES
// ============================================

async function loadAvaliacoes() {
    if (!currentUser) return;

    const container = document.getElementById('avaliacoes-list');
    container.innerHTML = '<p class="loading">Carregando avaliações...</p>';

    try {
        const response = await fetch(`${API_BASE}/avaliacoes/usuario/${currentUser.id}`);

        if (!response.ok) {
            container.innerHTML = '<p>Nenhuma avaliação encontrada.</p>';
            return;
        }

        const avaliacoes = await response.json();

        if (avaliacoes.length === 0) {
            container.innerHTML = '<p>Você ainda não tem avaliações. Comece uma agora!</p>';
            return;
        }

        let html = '<div class="avaliacoes-grid">';

        for (const avaliacao of avaliacoes) {
            const nivelClass = getNivelClass(avaliacao.nivelRisco);
            const statusClass = avaliacao.ativo ? 'status-ativo' : 'status-inativo';

            html += `
                <div class="avaliacao-card ${statusClass}">
                    <div class="avaliacao-header">
                        <h4>${new Date(avaliacao.dataCriacao).toLocaleDateString('pt-BR')}</h4>
                        <span class="nivel-risco ${nivelClass}">${avaliacao.nivelRisco || 'Pendente'}</span>
                    </div>
                    <div class="avaliacao-body">
                        <p><strong>Risco Total:</strong> ${avaliacao.porcentagemTotal}%</p>
                        <p><strong>Respostas:</strong> ${avaliacao.numRespostas} respostas</p>
                        <p><strong>Status:</strong> ${avaliacao.ativo ? 'Ativa' : 'Concluída'}</p>
                    </div>
                    <div class="avaliacao-actions">
                        <button class="btn btn-primary btn-small" onclick="viewAvaliacaoDetails('${avaliacao.id}')">Ver Detalhes</button>
                        ${avaliacao.ativo ? `<button class="btn btn-success btn-small" onclick="finalizarAvaliacao('${avaliacao.id}')">Finalizar</button>` : ''}
                    </div>
                </div>
            `;
        }

        html += '</div>';
        container.innerHTML = html;
    } catch (error) {
        console.error('Erro:', error);
        container.innerHTML = '<p>Erro ao carregar avaliações.</p>';
    }
}

async function createAvaliacao() {
    if (!currentUser) return;

    try {
        const response = await fetch(`${API_BASE}/avaliacoes/${currentUser.id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                usuarioId: currentUser.id
            })
        });

        if (response.ok) {
            const avaliacao = await response.json();
            currentAvaliacaoId = avaliacao.id;
            currentRespostas = {};

            // Recarrega perguntas para exibir
            await loadPerguntas();

            // Fecha qualquer modal aberto imediatamente para não bloquear interações
            closeModal();

            // Garante que o dashboard esteja visível e abre a aba Nova Avaliação
            showSection('dashboard');
            const tabBtn = document.querySelector(".tab-button[onclick*='nova-avaliacao']");
            switchTab('nova-avaliacao', tabBtn);

            // Rola para o questionário e foca
            const q = document.getElementById('questionnaire');
            if (q) { q.scrollIntoView({behavior: 'smooth', block: 'start'}); q.focus(); }

        } else {
            showModal('Erro', 'Falha ao criar avaliação.', 'error');
            setTimeout(() => closeModal(), 1200);
        }
    } catch (error) {
        console.error('Erro:', error);
        showModal('Erro', 'Erro ao conectar com o servidor.', 'error');
        setTimeout(() => closeModal(), 1200);
    }
}

async function loadPerguntas() {
    const container = document.getElementById('questionnaire');

    if (!currentAvaliacaoId) {
        container.innerHTML = `
            <button class="btn btn-primary" onclick="createAvaliacao()">Iniciar Nova Avaliação</button>
        `;
        return;
    }

    container.innerHTML = '<p class="loading">Carregando perguntas...</p>';

    try {
        const response = await fetch(`${API_BASE}/perguntas/ativas`);

        if (!response.ok) {
            container.innerHTML = '<p>Nenhuma pergunta disponível.</p>';
            return;
        }

        const perguntas = await response.json();

        if (perguntas.length === 0) {
            container.innerHTML = '<p>Nenhuma pergunta disponível no momento.</p>';
            return;
        }

        let html = '<form onsubmit="submitAvaliacacao(event)">';

        for (const pergunta of perguntas) {
            html += `
                <div class="questionnaire-item">
                    <h4>${pergunta.pergunta}</h4>
                    <small>Categoria: ${pergunta.categoria}</small>
                    <div id="opcoes-${pergunta.id}"></div>
                </div>
            `;
        }

        html += `
            <div class="questionnaire-actions">
                <button type="submit" class="btn btn-primary">Enviar Respostas</button>
                <button type="button" class="btn btn-secondary" onclick="cancelarAvaliacao()">Cancelar</button>
            </div>
        </form>`;

        container.innerHTML = html;

        // Carrega opções de resposta para cada pergunta
        for (const pergunta of perguntas) {
            await loadOpcoes(pergunta.id);
        }
    } catch (error) {
        console.error('Erro:', error);
        container.innerHTML = '<p>Erro ao carregar perguntas.</p>';
    }
}

async function loadOpcoes(perguntaId) {
    try {
        const response = await fetch(`${API_BASE}/opcoes/pergunta/${perguntaId}`);
        if (!response.ok) return;

        const opcoes = await response.json();
        const container = document.getElementById(`opcoes-${perguntaId}`);

        let html = '';
        for (const opcao of opcoes) {
            const inputId = `opt-${perguntaId}-${opcao.id}`;
            html += `
                <label class="radio-option" for="${inputId}" tabindex="0">
                    <input id="${inputId}" type="radio" name="pergunta-${perguntaId}" value="${opcao.id}">
                    <span>${opcao.texto || opcao.descricao || ''}</span>
                </label>
            `;
        }

        container.innerHTML = html;

        // Anexa ouvintes de evento de forma robusta
        const inputs = container.querySelectorAll('input[type="radio"]');
        inputs.forEach(inp => {
            inp.addEventListener('change', (e) => {
                currentRespostas[perguntaId] = e.target.value;
                console.log(`Resposta selecionada para pergunta ${perguntaId}: ${e.target.value}`);
            });
        });

        const labels = container.querySelectorAll('.radio-option');
        labels.forEach(lbl => {
            lbl.addEventListener('click', (e) => {
                const forId = lbl.getAttribute('for');
                const input = document.getElementById(forId);
                if (input) {
                    input.checked = true;
                    currentRespostas[perguntaId] = input.value;
                    console.log(`Label clicado - pergunta ${perguntaId}: ${input.value}`);
                }
            });
            // também permite seleção por teclado
            lbl.addEventListener('keydown', (e) => {
                if (e.key === 'Enter' || e.key === ' ') {
                    e.preventDefault();
                    lbl.click();
                }
            });
        });

    } catch (error) {
        console.error('Erro ao carregar opções:', error);
    }
}

async function submitAvaliacacao(event) {
    event.preventDefault();

    if (!currentAvaliacaoId) {
        showModal('Erro', 'ID da avaliação não encontrado.', 'error');
        setTimeout(() => closeModal(), 1200);
        return;
    }

    try {
        // Submete todas as respostas
        for (const [perguntaId, opcaoId] of Object.entries(currentRespostas)) {
            if (opcaoId) {
                await fetch(`${API_BASE}/respostas`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        avaliacaoId: currentAvaliacaoId,
                        opcaoRespostaId: opcaoId,
                        ativo: true
                    })
                });
            }
        }

        showModal('Sucesso', 'Respostas enviadas com sucesso!', 'success');
        setTimeout(() => closeModal(), 1000);
        currentRespostas = {};
        await loadPerguntas();
    } catch (error) {
        console.error('Erro:', error);
        showModal('Erro', 'Erro ao enviar respostas.', 'error');
        setTimeout(() => closeModal(), 1200);
    }
}

async function finalizarAvaliacao(avaliacaoId) {
    try {
        const response = await fetch(`${API_BASE}/avaliacoes/${avaliacaoId}/finalizar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const avaliacao = await response.json();
            showModal('Sucesso', `Avaliação finalizada! Nível de Risco: ${avaliacao.nivelRisco}`, 'success');
            // Recarrega a lista e garante que avaliação finalizada não apareça como ativa
            currentAvaliacaoId = null;
            await loadAvaliacoes();
            // volta para o dashboard para atualizar visualmente
            setTimeout(() => showSection('dashboard'), 800);
        } else {
            showModal('Erro', 'Falha ao finalizar avaliação.', 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        showModal('Erro', 'Erro ao conectar com o servidor.', 'error');
    }
}

async function cancelarAvaliacao() {
    currentAvaliacaoId = null;
    currentRespostas = {};
    await loadPerguntas();
}

async function viewAvaliacaoDetails(avaliacaoId) {
    currentAvaliacaoId = avaliacaoId;
    showSection('avaliacao-details');

    const container = document.getElementById('avaliacao-content');
    container.innerHTML = '<p class="loading">Carregando detalhes...</p>';

    try {
        const response = await fetch(`${API_BASE}/avaliacoes/${avaliacaoId}`);

        if (!response.ok) {
            container.innerHTML = '<p>Erro ao carregar avaliação.</p>';
            return;
        }

        const avaliacao = await response.json();
        const nivelClass = getNivelClass(avaliacao.nivelRisco);

        let html = `
            <div class="avaliacao-details">
                <div class="details-header">
                    <h3>Avaliação - ${new Date(avaliacao.dataCriacao).toLocaleDateString('pt-BR')}</h3>
                    <span class="nivel-risco ${nivelClass}">${avaliacao.nivelRisco || 'Pendente'}</span>
                </div>
                <div class="details-info">
                    <p><strong>Usuário:</strong> ${avaliacao.nomeUsuario}</p>
                    <p><strong>Risco Total:</strong> ${avaliacao.porcentagemTotal}%</p>
                    <p><strong>Total de Respostas:</strong> ${avaliacao.numRespostas}</p>
                    <p><strong>Data de Criação:</strong> ${new Date(avaliacao.dataCriacao).toLocaleDateString('pt-BR')}</p>
                    ${avaliacao.dataAtualizacao ? `<p><strong>Última Atualização:</strong> ${new Date(avaliacao.dataAtualizacao).toLocaleDateString('pt-BR')}</p>` : ''}
                </div>
        `;

        // Carrega respostas
        const respostasResponse = await fetch(`${API_BASE}/avaliacoes/${avaliacaoId}/respostas`);

        if (respostasResponse.ok) {
            const respostas = await respostasResponse.json();

            if (respostas.length > 0) {
                html += '<div class="respostas-section"><h4>Respostas</h4><ul>';

                for (const resposta of respostas) {
                    html += `<li>${resposta.opcaoResposta?.descricao || 'Resposta não disponível'}</li>`;
                }

                html += '</ul></div>';
            }
        }

        // Carrega simulações
        const simulacoesResponse = await fetch(`${API_BASE}/simulacoes/avaliacao/${avaliacaoId}`);

        if (simulacoesResponse.ok) {
            const simulacoes = await simulacoesResponse.json();

            if (simulacoes.length > 0) {
                html += `
                    <div class="simulacoes-section">
                        <h4>Simulações Associadas</h4>
                        <button class="btn btn-primary" onclick="showSection('simulacao')">Ver Simulações</button>
                    </div>
                `;
            }
        }

        html += '</div>';
        container.innerHTML = html;
    } catch (error) {
        console.error('Erro:', error);
        container.innerHTML = '<p>Erro ao carregar detalhes da avaliação.</p>';
    }
}

// ============================================
// FUNÇÕES DE TIPOS DE RISCO
// ============================================

async function loadTiposRisco() {
    const container = document.getElementById('tipos-risco-list');
    container.innerHTML = '<p class="loading">Carregando tipos de risco...</p>';

    try {
        const response = await fetch(`${API_BASE}/tipo-risco`);

        if (!response.ok) {
            container.innerHTML = '<p>Nenhum tipo de risco encontrado.</p>';
            return;
        }

        const tipos = await response.json();

        if (tipos.length === 0) {
            container.innerHTML = '<p>Nenhum tipo de risco disponível.</p>';
            return;
        }

        let html = '<div class="tipos-risco-grid">';

        for (const tipo of tipos) {
            const nivelClass = getNivelClass(tipo.nivelRisco);

            html += `
                <div class="tipo-risco-card">
                    <h4 class="nivel-risco ${nivelClass}">${tipo.nivelRisco}</h4>
                    <p><strong>Descrição:</strong> ${tipo.descricao || 'Sem descrição'}</p>
                    <p><strong>Range:</strong> ${tipo.porcentagemMinima}% - ${tipo.porcentagemMaxima}%</p>
                </div>
            `;
        }

        html += '</div>';
        container.innerHTML = html;
    } catch (error) {
        console.error('Erro:', error);
        container.innerHTML = '<p>Erro ao carregar tipos de risco.</p>';
    }
}

// ============================================
// FUNÇÕES DE SIMULAÇÃO
// ============================================

async function createSimulacao(event) {
    event.preventDefault();

    if (!currentAvaliacaoId) {
        showModal('Erro', 'Nenhuma avaliação selecionada.', 'error');
        return;
    }

    const tipoRiscoId = document.getElementById('simulacao-tipo').value;
    const cenario = document.getElementById('simulacao-cenario').value;
    const impactoFinanceiro = parseFloat(document.getElementById('simulacao-impacto-financeiro').value);
    const impactoOperacional = document.getElementById('simulacao-impacto-operacional').value;

    try {
        const response = await fetch(`${API_BASE}/simulacoes`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                avaliacaoId: currentAvaliacaoId,
                tipoRiscoId: tipoRiscoId,
                descricaoCenario: cenario,
                impactoFinanceiro: impactoFinanceiro,
                impactoOperacional: impactoOperacional,
                ativo: true
            })
        });

        if (response.ok) {
            showModal('Sucesso', 'Simulação criada com sucesso!', 'success');
            document.getElementById('simulacao-form').reset();
            setTimeout(() => showSection('simulacao'), 1500);
        } else {
            showModal('Erro', 'Falha ao criar simulação.', 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        showModal('Erro', 'Erro ao conectar com o servidor.', 'error');
    }
}

// ============================================
// FUNÇÕES UTILITÁRIAS
// ============================================

function getNivelClass(nivel) {
    if (!nivel) return 'nivel-pendente';

    const nivelLower = nivel.toLowerCase();

    if (nivelLower.includes('alto')) return 'nivel-alto';
    if (nivelLower.includes('médio') || nivelLower.includes('medio')) return 'nivel-medio';
    if (nivelLower.includes('baixo')) return 'nivel-baixo';

    return 'nivel-pendente';
}

function showModal(title, message, type = 'info') {
    const modal = document.getElementById('modal');
    const modalBody = document.getElementById('modal-body');

    let icon = '✓';
    let className = 'modal-info';

    if (type === 'error') {
        icon = '✗';
        className = 'modal-error';
    } else if (type === 'success') {
        icon = '✓';
        className = 'modal-success';
    } else if (type === 'warning') {
        icon = '⚠';
        className = 'modal-warning';
    }

    modalBody.innerHTML = `
        <div class="${className}">
            <div class="modal-icon">${icon}</div>
            <h3>${title}</h3>
            <p>${message}</p>
        </div>
    `;

    // mostra o modal (classe + style para garantir overlay removido depois)
    modal.classList.add('show');
    modal.style.display = 'flex';
}

function closeModal() {
    const modal = document.getElementById('modal');
    modal.classList.remove('show');
    modal.style.display = 'none';
}

// Fechar modal ao clicar fora dele (usa closeModal)
window.onclick = function(event) {
    const modal = document.getElementById('modal');
    if (event.target === modal) {
        closeModal();
    }
}

// ============================================
// INICIALIZAÇÃO
// ============================================

document.addEventListener('DOMContentLoaded', function() {
    // Recupera usuário do localStorage se existir
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
        currentUser = JSON.parse(savedUser);
        updateNavbar();
        // garante que o nome e email do usuário sejam exibidos no dashboard se a seção for mostrada
        const nameElem = document.getElementById('user-name');
        const emailElem = document.getElementById('user-email');
        if (nameElem) nameElem.textContent = currentUser.nome || '';
        if (emailElem) emailElem.textContent = currentUser.email || '';
    }

    // Mostra a seção inicial
    showSection('home');
});

