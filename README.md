# darch
experimental integration architecture for Android/Kotlin

## Summary
This architecture looks similar with MVVM, but they are different.

MVVM has two reactive layer(its called ViewModel and Model), but darch's ViewModel and Model are not necessary reactive.

darch elements:
- ReactiveProperty
  - components of property binding(one-way or two-way) same as MVVM
- ReactiveCommand
  - command to change model state, same as void function
- ActiveQuery
  - query to get model state, but not to change model state
- Stream
  - this components looks similar with C#/.NET's ObservableCollection, but specializing in RecyclerView

## License
MIT License.

### Using
- Kotlin Libraries, [Apache License 2.0](https://github.com/JetBrains/kotlin/tree/master/license)
- Kotlin Coroutines, [Apache License 2.0](https://github.com/Kotlin/kotlinx.coroutines/blob/master/LICENSE.txt)
- Koin, [Apache License 2.0](https://github.com/InsertKoinIO/koin/blob/master/LICENSE)