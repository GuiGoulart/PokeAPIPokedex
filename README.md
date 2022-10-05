# PokeApi Podex
O aplicativo usa um conjunto de bibliotecas do Android Jetpack mais Retrofit para exibir dados da API REST. O aplicativo usa Kotlin.

### Prerequisites
O projeto tem todas as dependências necessárias nos arquivos gradle. Adicione o projeto ao Android Studio ou Intelij e construa. Todas as dependências necessárias serão baixadas e instaladas.

## Arquitetura
O projeto usa o padrão de arquitetura MVVM

## Bibliotecas

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Gerencia dados relacionados à interface do usuário de maneira ciente do ciclo de vida e atue como um canal entre os casos de uso e a interface do usuário.
* [ViewBinding](https://developer.android.com/topic/libraries/data-binding) - Biblioteca de suporte que permite vincular componentes de interface do usuário em layouts a fontes de dados, vincula detalhes de caracteres e resultados de pesquisa à interface do usuário.
* [Dagger Hilt](https://dagger.dev/hilt/) - Para injeção de dependência.
* [Retrofit](https://square.github.io/retrofit/) - Para acessar a API Rest
* [Shimmer](http://facebook.github.io/shimmer-android/) - Para fazer uma tela de carregamento personalizada
* [Coroutines](https://developer.android.com/kotlin/coroutines) - Para fazer chamadas assíncrona.
* [Navigation](https://developer.android.com/guide/navigation?gclid=Cj0KCQjw1vSZBhDuARIsAKZlijQJ3XnsaaoPInzVx8yar8xFRQoS0JXqjZ278w8AHUhZ6VuStSKzuy8aAqeSEALw_wcB&gclsrc=aw.ds) - Para fazer a nevagação de uma tela para outra utilizando fragment assim como transacionar dados de um fragment para outro. Utilizando animação para a transação de tela
* [Mockk](https://mockk.io/) - Para teste unitario.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - Para a criação de banco local.

## Screenshots Portrait Events
![image](https://user-images.githubusercontent.com/55321777/194098285-44f52db4-afb4-4837-b073-2e32f7261e1a.png)

## Screenshots Portrait Detail
![image](https://user-images.githubusercontent.com/55321777/194098377-088bb44a-da2e-40e5-8898-b3345bb9c4a5.png)


