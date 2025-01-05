package com.example.gadgetlist.ui

import android.text.Layout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.gadgetlist.R
import com.example.gadgetlist.data.Good
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.gadgetlist.ui.theme.GadgetListTheme

@Composable
fun InventoryList(
    itemList: List<Good>,
    onItemClick: (Good) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { good ->
            GadgetCard(good = good,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(good) })
        }
    }
}

@Composable
private fun GadgetCard(
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
                Text(
                    text = good.quantity.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier=Modifier.align(Alignment.Bottom)

                )
            }
            Text(
                text = stringResource(R.string.in_stock, good.quantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
fun GadgetCardPreview(){
    GadgetListTheme {
        InventoryList(
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
                ),
        )
    }

}