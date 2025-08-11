import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.presentation.composables.components.ButtonFilter
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.components.RefreshIndicator
import com.example.rickandmorty.presentation.composables.sections.CharacterFilterScreen
import com.example.rickandmorty.presentation.composables.sections.CharactersGridScreen
import com.example.rickandmorty.presentation.composables.sections.SearchBar
import com.example.rickandmorty.theme.RickAndMortyTheme
import com.example.rickandmorty.utils.rememberFakeLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun MainScreenContent(
    rickAndMortyCharacters: LazyPagingItems<RickAndMortyCharacter>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterChange: (CharacterStatus?, CharacterGender?) -> Unit,
    isRefreshing: Boolean = false
) {
    var showFilter by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()

    // Скролл вверх при обновлении списка
    LaunchedEffect(rickAndMortyCharacters.loadState.refresh) {
        if (rickAndMortyCharacters.loadState.refresh is LoadState.NotLoading &&
            rickAndMortyCharacters.itemCount > 0
        ) {
            gridState.scrollToItem(0)
        }
    }

    val isFirstLoad = rickAndMortyCharacters.loadState.refresh is LoadState.Loading &&
            rickAndMortyCharacters.itemCount == 0

    Scaffold(
        floatingActionButton = {
            ButtonFilter(onClick = { showFilter = true })
        }
    ) { paddingValues ->

        RickAndMortyTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(10.dp)
            ) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange
                )

                Spacer(modifier = Modifier.size(10.dp))

                if (isFirstLoad) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadIndicator()
                    }
                } else {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing),
                        onRefresh = { coroutineScope.launch { rickAndMortyCharacters.refresh() } },
                        indicator = { state, _ ->
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(top = 12.dp),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                RefreshIndicator(isRefreshing = state.isRefreshing)
                            }
                        }
                    ) {
                        CharactersGridScreen(
                            modifier = Modifier.fillMaxWidth(),
                            rickAndMortyCharacters = rickAndMortyCharacters,
                            gridState = gridState
                        )
                    }
                }
            }
        }

        if (showFilter) {
            CharacterFilterScreen(
                onApplyFilter = { status, gender ->
                    onFilterChange(status, gender)
                    showFilter = false
                }
            )
        }
    }
}

@Preview
@Composable
fun MainScreenContentPreview() {

    val fakeList = List(6) {
        RickAndMortyCharacter(
            id = 1,
            name = "Character $it",
            status = CharacterStatus.ALIVE,
            species = "Human",
            gender = CharacterGender.UNKNOWN,
            image = ""
        )
    }

    val pagingItems = rememberFakeLazyPagingItems(fakeList)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MainScreenContent(
            rickAndMortyCharacters = pagingItems,
            searchQuery = "",
            onSearchQueryChange = {},
            onFilterChange = { _, _ -> }
        )
    }
}
