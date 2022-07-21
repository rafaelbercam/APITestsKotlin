# __Testes de API em Kotlin__


## __Pré-requisitos__
1. Instalar o [Kotlin](https://kotlinlang.org/docs/command-line.html)

## __Ambiente__
Para executar os testes localmente, estou utilizando o ServeRest

<p align="center">
 <img alt="Logo do ServeRest" src="https://user-images.githubusercontent.com/29241659/115161869-6a017e80-a076-11eb-9bbe-c391eff410db.png" height="120">
</p>

Link do Repo: https://github.com/ServeRest/ServeRest

ServeRest está disponível de forma [online](https://serverest.dev), no [npm](https://www.npmjs.com/package/serverest) e no [docker](https://hub.docker.com/r/paulogoncalvesbh/serverest/).

## __Instalando Dependências__

###Instalar via [Gradle](https://gradle.org/)



## __Configuração do Projeto__
### Estrutura de Pastas
O projeto esta dividido da seguinte maneira:

    [APITestsKotlin]
       [src] -> código fonte
            [core] -> setup do requestspecification do REST-assured
            [factory] -> Properties que retornam objetos criados através de uma sererialização
            [requests] -> Métodos que retornam o objeto Response do REST-assured
            [runner] -> Runner do JUnit
            [tests] -> Arquivos de teste do JUnit
