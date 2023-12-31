package com.foodwaste.mubazir.presentation.browse.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.foodwaste.mubazir.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onSearch : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            keyboardController?.hide()
            focusManager.clearFocus()
            onSearch(it)
        },
        active = false,
        onActiveChange = {newActiveState ->
            if (!newActiveState) {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.text_search_surplus_food))
        },
        trailingIcon = {
            IconButton(onClick = {
                onFilterClick()
            }) {
                Icon(imageVector = Icons.Filled.FilterList, contentDescription = null)
            }
        },
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
    ) {

    }
}
