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
![image](https://user-images.githubusercontent.com/55321777/157301552-54a37b31-9c29-47cd-abc8-3468605932f1.png)|![image](https://user-images.githubusercontent.com/55321777/157301593-4eb8a046-6be4-4af8-a094-adac5c482cc4.png)|![image](https://user-images.githubusercontent.com/55321777/157301636-57608ed8-1513-4948-a262-24277d3cba3b.png)

## Screenshots Land
![image](https://user-images.githubusercontent.com/55321777/157302110-9ca344bb-5caa-4eab-a3ca-9ffd46b76408.png)|![image](https://user-images.githubusercontent.com/55321777/157302129-99b32ff3-241c-4e37-b373-5997f7ce148f.png)|![image](https://user-images.githubusercontent.com/55321777/157302160-076c2746-fc2e-4463-a714-3f6a5d3dc1df.png)

##Demo





