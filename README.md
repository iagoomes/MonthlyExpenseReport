# MonthlyExpenseReport
Tech challenge fase 1
3

### Visão Geral

O projeto consiste no desenvolvimento de uma API destinada à análise de faturas de cartões de crédito, complementada por um dashboard intuitivo que auxilia os usuários na estruturação e acompanhamento de seus gastos. A principal finalidade é fornecer uma ferramenta robusta que ajude os usuários a gerenciar melhor suas finanças pessoais e tomar decisões financeiras mais informadas.

### Funcionalidades Principais

1. **Análise de Faturas de Cartões de Crédito**
    - **Importação e Processamento**: A API permite a importação de faturas de cartões de crédito em PDF. As informações das faturas são processadas para identificar e categorizar os gastos.
    - **Classificação de Gastos**: Os usuários podem categorizar suas despesas em diferentes grupos, como gastos essenciais, lazer, educação, entre outros.
2. **Dashboard Personalizado**
    - **Visualização de Gastos**: O dashboard oferece visualizações gráficas e tabelares dos gastos categorizados, permitindo uma análise detalhada e intuitiva.
    - **Parâmetros Personalizados**: Os usuários podem definir parâmetros personalizados para o monitoramento de suas despesas, adaptando a ferramenta às suas necessidades específicas.
3. **Gerenciamento de Orçamento**
    - **Orçamento Mensal**: Os usuários podem definir um orçamento mensal e acompanhar o cumprimento desse orçamento em tempo real.
    - **Alertas e Recomendações**: A ferramenta envia alertas quando os gastos estão próximos de exceder o orçamento definido e oferece recomendações para ajustes.
4. **Educação Financeira e Produtos de Investimento**
    - **Dicas e Sugestões**: Com base nos padrões de gastos e nas metas financeiras dos usuários, a API fornece dicas para melhorar a saúde financeira.
    - **Produtos de Renda Fixa**: A plataforma oferece opções de produtos de renda fixa, ajudando os usuários a investir de maneira segura e eficiente, alinhada aos seus objetivos financeiros.

### Benefícios

- **Controle Financeiro**: Facilita o controle e a gestão das finanças pessoais, ajudando os usuários a evitar gastos desnecessários e a economizar.
- **Planejamento**: Suporte ao planejamento financeiro, permitindo que os usuários alcancem suas metas de maneira organizada e prática.
- **Educação e Crescimento**: Promove a educação financeira, incentivando hábitos de consumo conscientes e investimentos inteligentes.

### Público-Alvo

- Indivíduos que desejam ter um maior controle sobre suas finanças pessoais.
- Pessoas interessadas em melhorar sua educação financeira e aprender sobre investimentos seguros.
- Usuários que buscam uma ferramenta prática e personalizada para gerenciar seus gastos e planejar seu orçamento mensal.



# Event Storming

## **Preparação**

Envolvidos: Suellen, Iago Gomes, Shai, Yago, Fernando Aragão

## **Identificação dos Eventos**

<aside>
![image](https://github.com/user-attachments/assets/d3149bda-fe98-46ee-a898-435734b50105)
</aside>

## **Organização dos Eventos**

- Agrupar eventos relacionados e organizar na sequência correta.
- Identificar e remover duplicatas ou eventos desnecessários.

<aside>
![image](https://github.com/user-attachments/assets/1b4b472c-ac78-4290-9877-d10d45c9838f)
</aside>

## Identificar Pontos de Atenção

- Identificar possíveis problemas ou regras de negocios que devem ser estabelecidas

<aside>
![image](https://github.com/user-attachments/assets/0c52dfe6-10e8-4fd5-ba15-b62f14f0394e)
</aside>

## Aplicar Elementos Pivotais

- Identificar mudanças de estado ou mudanças de estados

<aside>
![image](https://github.com/user-attachments/assets/f8352b7a-545b-4328-8c24-7a1e1dbc3da1)
</aside>

## Colocar Ator, Comando e Politica

- Identificar ações que iniciam eventos (comandos do usuário ou do sistema).
- Conectar comandos aos eventos correspondentes.

<aside>
![image](https://github.com/user-attachments/assets/1727aea8-1c31-4690-ae61-3730ed071626)
</aside>

## **Definição de Modelos de Leitura**

- Definir os modelos de leitura que serão usados para consultas e visualização de dados.
- Associar os eventos que atualizam esses modelos de leitura.

<aside>
![image](https://github.com/user-attachments/assets/be38938e-739e-4d87-a240-d0ee2de8b0c4)
</aside>

## **Definição de Agregados**

- Identificar entidades principais que agregam eventos (agregados).
- Adicionar agregados no mural e associar eventos a eles.
  
<aside>
⚠️ Colocar foto

</aside>
