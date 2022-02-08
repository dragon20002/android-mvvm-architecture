# Jetpack -Compose

## 상태 관리

### 1. Composable

@Composable 내에서 상태관리

### 2. State Holder

```java
/* State Holder */
class AppState --> `ViewModel`
{
    ...control views...
}
```
```java
// `remember Composable`에서 State Holder 사용
@Composable
fun rememberAppState( --> `DI`
    ...required viewStates..., res
) = remember(...{required viewStates}...) {
    AppState(...)
}
```
```java
// 뷰에서 `remember Composable` 사용
@Composable
fun App() {
    AppTheme {
        val appState = rememberAppState()
        Scaffold(
            scaffoldState = appState.scaffoldState,
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    BottomBar(
                        tabs = appState.bottomBarTabs,
                        ...
                    )
                }
            }
        ) {
            ...children...
        }
    }
}
```

### 3. [ViewModel](https://developer.android.com/jetpack/compose/state#viewmodels-source-of-truth)

```java
// Model
data class ExampleUiState(
    dataToDisplayOnScreen: List<Example> = emptyList(),
    userMessages: List<Message> = emptyList(),
    loading: Boolean = false
)
```
```java
// Action
class ExampleViewModel(
    private val repository: MyRepository,
    private val savedState: SavedStateHandle
) : ViewModel() {

    // has model
    var uiState by mutableStateOf<ExampleUiState>(...)
        private set

    /* ... Actions ... */
    // Business logic
    fun somethingRelatedToBusinessLogic() { ... }
}
```
```java
@Composable
fun ExampleScreen(viewModel: ExampleViewModel = viewModel()) {

    val uiState = viewModel.uiState // get model
    ...

    // bind data and actions
    Button(onClick = { viewModel.somethingRelatedToBusinessLogic() }) {
        Text("Do something")
    }
}
```

### 4. [ViewModel&StateHolder](https://developer.android.com/jetpack/compose/state#viewmodel-state)

```java
private class ExampleState(
    val lazyListState: LazyListState,
    private val resources: Resources,
    private val expandedItems: List<Item> = emptyList()
) { ... }
```
```java
@Composable
private fun rememberExampleState(...) { ... }
```
```java
@Composable
fun ExampleScreen(viewModel: ExampleViewModel = viewModel()) {

    val uiState = viewModel.uiState // ViewModel: 비즈니스 로직
    val exampleState = rememberExampleState() // StateHolder: UI로직/UI요소 상태 관리

    LazyColumn(state = exampleState.lazyListState) {
        items(uiState.dataToDisplayOnScreen) { item ->
            if (exampleState.isExpandedItem(item) {
                ...
                Button(onClick = { viewModel.somethingRelatedToBusinessLogic() }) {
                    Text("Do something")
                }
                ...
            }
            ...
        }
    }
}
```

- 수명주기를 다르게 관리할 수 있음

- 비즈니스 로직과 UI 로직을 분리할 수 있음

## 부수 효과

