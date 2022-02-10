# Jetpack -Compose

## 상태 관리

### 1. Composable

@Composable 내에서 상태관리

```java
@Composable
fun MyApp() {
    MyTheme {
        val scaffoldState = rememberScaffoldState() // useRef ScaffoldState
        val coroutineScope = rememberCoroutineScope() // useCoroutineScope

        Scaffold(scaffoldState = scaffoldState) {
            MyContent(
                showSnackbar = { message ->
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message)
                    }
                }
            )
        }
    }
}
```

### 2. State Holder

```java
/* State Holder */
// Plain class that manages App's UI logic and UI elements' state
class MyAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val resources: Resources,
    /* ... */
) {
    val bottomBarTabs = /* State */

    // Logic to decide when to show the bottom bar
    val shouldShowBottomBar: Boolean
        get() = /* ... */

    // Navigation logic, which is a type of UI logic
    fun navigateToBottomBarRoute(route: String) { /* ... */ }

    // Show snackbar using Resources
    fun showSnackbar(message: String) { /* ... */ }
}

/* remember Composable */
@Composable
fun rememberMyAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    resources: Resources = LocalContext.current.resources,
    /* ... */
) = remember(scaffoldState, navController, resources, /* ... */) {
    // 필요한 상태값에 대한 `remember`를 `State Holder`로 한번에 가져옴
    MyAppState(scaffoldState, navController, resources, /* ... */)
}
```

```java
@Composable
fun MyApp() {
    MyTheme {
        // 필요한 상태값에 대한 `remember`를 `State Holder`로 한번에 가져옴
        val myAppState = rememberMyAppState()

        Scaffold(
            scaffoldState = myAppState.scaffoldState,
            bottomBar = {
                if (myAppState.shouldShowBottomBar) {
                    BottomBar(
                        tabs = myAppState.bottomBarTabs,
                        navigateToRoute = {
                            myAppState.navigateToBottomBarRoute(it)
                        }
                    )
                }
            }
        ) {
            NavHost(navController = myAppState.navController, "initial") { /* ... */ }
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
) {
    val bottomBarTabs = /* State */

    // Logic to decide when to show the bottom bar
    val shouldShowBottomBar: Boolean
        get() = /* ... */

    // Navigation logic, which is a type of UI logic
    fun navigateToBottomBarRoute(route: String) { /* ... */ }

    // Show snackbar using Resources
    fun showSnackbar(message: String) { /* ... */ }

    // Check if item is expanded
    fun isExpandedItem(item: Item) { /* ... */ }
}

@Composable
private fun rememberExampleState(
    lazyListState: LazyListState = rememberLazyListState(),
    resources: Resources = LocalContext.current.resources,
) = remember(lazyListState, resources) {
    ExampleState(lazyListState, resources)
}
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
            }
        }
    }
}
```

- 수명주기를 다르게 관리할 수 있음

- 비즈니스 로직과 UI 로직을 분리할 수 있음

## Code Snippet

### 상태값 입출력

```java
import androidx.compose.runtime.mutableStateOf

@Composable
fun TodoInputTextField(modifier: Modifier) {
   val (text, setText) = remember { mutableStateOf("") }
   TodoInputText(text, setText, modifier)
}
```

```java
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

var text by rememberSaveable { mutableStateOf("") }

OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Label") }
)
```

