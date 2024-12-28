package com.example.gadgetlist.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gadgetlist.R
import com.example.gadgetlist.data.Good
import com.example.gadgetlist.ui.theme.GadgetListTheme
import androidx.compose.material3.Icon

@Composable
fun GadgetApp(
    goodList:List<Good>
) {
    Scaffold(
        topBar ={
            TopRow(Modifier)
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(goodList) {
                GadgetItem(good = it,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            }
        }

    }

}
@Composable
fun GadgetList(
    itemList: List<Good>,
    onItemClick: (Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { good ->
            GadgetItem(good = good,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(good.id) })
        }
    }
}

@Composable
fun GoodItem(
    good: Good,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = good.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = good.price.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = stringResource(R.string.in_stock, good.quantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/**
 * Composable that displays a photo of a dog.
 *
 * @param dogIcon is the resource ID for the image of the dog
 * @param modifier modifiers to set to this composable
 */


@Composable
fun GoodIcon(
    goodIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.medium),
        painter = painterResource(goodIcon),

        // Content Description is not needed here - image is decorative, and setting a null content
        // description allows accessibility services to skip this element during navigation.

        contentDescription = null
    )
}

@Composable
fun GoodInformation(
    GoodName: String,
    goodPrice: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = GoodName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = stringResource(R.string.item_price, goodPrice),
            style = MaterialTheme.typography.bodyLarge,

            )
    }
}

@Composable
fun GadgetItem(
    good: Good,
    modifier: Modifier = Modifier
) {
    Card( modifier=modifier
        .clip(MaterialTheme.shapes.small),){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))

        ) {
            GoodIcon(good.imageResourceId)
            GoodInformation(good.name, good.price)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GadgetTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRow(
    modifier:Modifier = Modifier
){
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource( R.drawable.tech_icon),
                    contentDescription = null,
                )
                Text(
                    text = "Tech Online"
                )
            }
        },
        modifier = modifier,
    )

}

@Preview(showBackground = true)
@Composable
fun GadgetListPreview() {
   GadgetListTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            GadgetList(

                onItemClick = {},
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier,
                itemList = listOf(
                    Good(
                        id = 1,
                        name = "iPhone 11",
                        category = "Phone",
                        price = 1300,
                        quantity = 10,
                        description = "expensive iphone"
                    ),
                    Good(
                        id = 2,
                        name = "iPhone 11",
                        category = "Phone",
                        price = 1300,
                        quantity = 10,
                        description = "expensive iphone"
                    )
                )
            )
        }

    }
}

@Preview
@Composable
fun GadgetAppPreview(){
    GadgetListTheme {
        GadgetApp(
            listOf(
                    Good(
                        id = 1,
                        name = "iPhone 11",
                        category = "Phone",
                        price = 1300,
                        quantity = 10,
                        description = "expensive iphone"
                    ),
                    Good(
                        id = 2,
                        name = "iPhone 11",
                        category = "Phone",
                        price = 1300,
                        quantity = 10,
                        description = "expensive iphone"
                    )
                )
        )
    }
}

