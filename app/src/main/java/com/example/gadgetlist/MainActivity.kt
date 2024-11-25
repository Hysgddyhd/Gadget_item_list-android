package com.example.gadgetlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gadgetlist.ui.theme.GadgetListTheme
import com.example.gadgetlist.Good

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GadgetListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GadgetApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoodPreview() {
   GadgetListTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            GadgetApp()
        }
    }
}

@Composable
fun GadgetApp() {
    Scaffold(
        topBar ={
            TopRow(Modifier)
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(goods) {
                GoodItem(good = it,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            }
        }
    }

}
@Composable
fun GoodItem(
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

/**
 * Composable that displays a photo of a dog.
 *
 * @param dogIcon is the resource ID for the image of the dog
 * @param modifier modifiers to set to this composable
 */


@Composable
fun GoodIcon(
    @DrawableRes goodIcon: Int,
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
    @StringRes GoodName: Int,
    goodPrice: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(GoodName),
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