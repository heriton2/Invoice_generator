# Invoice Generator - Backend

Este é o backend para o sistema de geração de faturas a partir de um arquivo CSV. O objetivo é permitir que os usuários importem um arquivo CSV contendo dados de cobrança e, em seguida, o sistema irá gerar uma fatura com base nesses dados, seguindo um modelo semelhante a uma fatura de cartão de crédito.

## Tecnologia

- Backend: Java
- Frontend: Angular

## Requisitos

- Importar os dados do arquivo CSV: O usuário poderá enviar um arquivo CSV para o sistema, que irá processar os dados e gerar a fatura.
- Salvar os registros: Os registros importados serão salvos no sistema com as seguintes informações: data de criação, hora de criação e status.
- Status dos registros: Os registros poderão ter os seguintes status: PENDENTE, CANCELADO, ERRO, BLOQUEADO, CONCLUÍDO.

## Executando o Projeto Spring com Maven

Para executar o projeto Spring com Maven em sua máquina, siga as etapas abaixo:

1. Certifique-se de que o Maven esteja instalado em seu sistema. Você pode verificar a instalação executando o seguinte comando no terminal:

```
mvn -version
```

2. Faça o clone do repositório do projeto do GitHub.

```
git clone <link_do_repositorio>
```

3. Navegue até o diretório raiz do projeto.

```
cd <diretorio_do_projeto>
```

4. Execute o seguinte comando para compilar e empacotar o projeto em um arquivo JAR:

```
mvn clean package
```

5. Após a conclusão do empacotamento, execute o seguinte comando para iniciar o servidor:

```
java -jar target/nome_do_arquivo.jar
```

Isso iniciará o servidor Spring e tornará o aplicativo acessível localmente.

6. Para acessar o endereço padrão do H2, abra o navegador e digite o seguinte URL:

```
http://localhost:8080/h2-console
```

Isso abrirá a interface de administração do H2, onde você poderá interagir com o banco de dados embutido.

Certifique-se de que as configurações do H2 no projeto estejam corretas para acessar o banco de dados corretamente.

7. Além disso, você pode acessar a documentação da API no Swagger. Abra o navegador e digite o seguinte URL:

```
http://localhost:8080/swagger-ui/index.html
```
ou
```
https://invoicegenerator-heriton2.b4a.run/swagger-ui/index.html
```

Isso abrirá a interface do Swagger, onde você pode explorar e testar os endpoints da API.

Lembre-se de que o número da porta pode variar dependendo da configuração do seu aplicativo. Consulte a documentação do projeto ou o arquivo de configuração para obter informações mais detalhadas.

Agora você está pronto para executar o projeto Spring com Maven em sua máquina, acessar o endereço padrão do H2 para administrar o banco de dados e explorar a documentação da API no Swagger.

### Modelo do arquivo CSV

O arquivo CSV deve seguir o seguinte modelo: [Modelo_CSV](https://docs.google.com/spreadsheets/d/1kwpgti04D1DJwyvXV0iS6wvgyDJXXLiZ2mYhrozv2HM/edit?usp=sharing).

## Fluxo de funcionamento

### Passo 1 - Upload do arquivo CSV

- O usuário faz o upload do arquivo CSV.
- O sistema valida a estrutura das colunas. Se a estrutura estiver divergente do modelo, o sistema exibe uma mensagem de erro e não permite a importação.

### Passo 2 - Importação do conteúdo do arquivo CSV

- Se o arquivo for carregado com sucesso, o sistema inicia o processo de importação dos dados.
- A data de vencimento da fatura será calculada a partir do número de dias especificado na coluna C, a partir da data atual.
- Após a importação, os registros serão colocados no status PENDENTE.

### Passo 3 - Gerando a fatura

- O sistema realizará o cálculo para gerar a fatura de cobrança com base nos dados do arquivo.
- Durante o processamento, se o valor da cobrança for superior a um determinado valor limite (por padrão, R$ 25.000), o status do registro será definido como BLOQUEADO.
- Será calculado o valor total da fatura, multiplicando a quantidade do item pelo valor unitário para cada serviço.

### Tratamento de erros

- Durante o processo de importação dos dados, se um CNPJ inválido for detectado, ou seja, um número digitado incorretamente, a linha não será importada e o status do registro será definido como ERRO. A mensagem de erro será salva para explicação: "Número CNPJ inválido. Cobrança não gerada".
- Os serviços que aparecerão na fatura de cobrança são: "Mensalidade Plataforma" e "Emissão de cartão".
