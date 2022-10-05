# PokeApi Podex
O aplicativo usa um conjunto de bibliotecas do Android Jetpack mais Retrofit para exibir dados da API REST. O aplicativo usa Kotlin.

### Prerequisites
O projeto tem todas as dependências necessárias nos arquivos gradle. Adicione o projeto ao Android Studio ou Intelij e construa. Todas as dependências necessárias serão baixadas e instaladas.

## Arquitetura
O projeto usa o padrão de arquitetura MVVM

## Bibliotecas

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Gerencia dados relacionados à interface do usuário de maneira ciente do ciclo de vida e atue como um canal entre os casos de uso e a interface do usuário.
* [ViewBinding](https://developer.android.com/topic/libraries/data-binding) - Biblioteca de suporte que permite vincular componentes de interface do usuário em layouts a fontes de dados, vincula detalhes de caracteres e resultados de pesquisa à interface do usuário.
* [Koin](https://insert-koin.io/) - Para injeção de dependência.
* [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=in) - Permitir a paginação dos dados.
* [Retrofit](https://square.github.io/retrofit/) - Para acessar a API Rest
* [Coroutines](https://developer.android.com/kotlin/coroutines) - Para fazer chamadas assíncrona.
* [Palette](https://developer.android.com/training/material/palette-colors) - Para capturar a cor mais presente dentro de uma imagem.
* [ProgressView](https://github.com/skydoves/ProgressView) - Para fazer animação e exibições personalizadas da progressBar horizontal.
* [Mockk](https://mockk.io/) - Para teste unitario.
* [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - Para mock da resposta do servidor.
* [Espresso](https://developer.android.com/training/testing/espresso) - Para teste de UI.
* [Lottie](https://airbnb.design/lottie/) - Para a crianção de animações.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - Para a criação de banco local.

## Screenshots Portrait
![image](https://user-images.githubusercontent.com/55321777/157317002-31ef8b39-0a9d-44d3-a3b5-30dd97d73811.png)|![image](https://user-images.githubusercontent.com/55321777/157301593-4eb8a046-6be4-4af8-a094-adac5c482cc4.png)|![image](https://user-images.githubusercontent.com/55321777/157317068-82bac2ce-082f-477c-982d-faa991280f68.png)

## Screenshots Land
![image](https://user-images.githubusercontent.com/55321777/157317238-b140e142-a44b-4cdf-b635-08e7ace559b8.png)|![image](https://user-images.githubusercontent.com/55321777/157302129-99b32ff3-241c-4e37-b373-5997f7ce148f.png)|![image](https://user-images.githubusercontent.com/55321777/157317260-62bc3605-46e1-4a53-8c04-f143f97a682f.png)


