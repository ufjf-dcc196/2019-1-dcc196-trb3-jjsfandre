# 2019-1-dcc196-trb3-jjsfandre
2019-1-dcc196-trb3-jjsfandre created by GitHub Classroom

Head Hunter App

Autor: Jônatas Sousa de Faria André
Matrícula: 201676030
E-mail: jjsfandre@gmail.com

*** Estrutura de banco utilizada ***

Foram criadas as seguintes tabelas:

* Candidato
* Categoria
* Producao
* Atividade


O relacionamento entre elas se dá da seguinte forma:

Cada Candidato possui 0 ou mais Produções em seu nome (-> 0..N)
Cada Produção pertence à um Candidato (-> 1)
Cada Categoria pode estar associada à uma ou mais Produções (-> 0..N)
Cada Produção pertence à uma Categoria (-> 1)
Cada Produção possui 0 ou mais Atividades associadas à ela (-> 0..N)
Cada Atividade pertence à uma Produção (-> 1)


*** Modelo Java utilizado ***


Foram criadas quatro classes Java, uma para cada tabela principal do banco:

* Atividade
* Candidato
* Categoria
* Producao


Além dos atributos básicos de cada classe (seguindo a especificação), atributos foram criados para estabelecer os relacionamentos entre classes. São eles:

* Atividade
	-> producaoId

* Producao
	-> categoriaId
	-> candidatoId

Além desses atributos, o atributo sumHorasAtividades foi criado em duas classes para facilitar o desenvolvimento da feature de listagem de Candidatos por Categoria ordenados pela soma de horas das atividades. Esse atributo foi criado nas classes Candidato e Producao.