package com.sw.newsapi.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.glide.GlideImage
import com.sw.newsapi.api.model.Article
import com.sw.newsapi.api.model.Source
import java.net.URLEncoder

@Composable
fun NewsRow(
    navController: NavHostController,
    subject: String,
    article: Article,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = {
                    navController.navigate("newsDetails/${URLEncoder.encode(article.url, "utf-8")}/${subject}")
                }
            )
            .padding(bottom = 4.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, MaterialTheme.colors.secondary), RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface)
            .padding(8.dp)
            .fillMaxWidth()

    ) {
        Surface(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(6.dp)),
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            if (article.urlToImage != "") GlideImage(
                imageModel = article.urlToImage,
                modifier = modifier,
                // shows a progress indicator when loading an image.
                loading = {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val indicator = createRef()
                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(indicator) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )
                    }
                },
                // shows an error text message when request failed.
                failure = {
                    Text(text = "X")
                })
        }
        Column(

            modifier = modifier
                .padding(start = 8.dp)
        ) {
            Text(
                article.title ?: "--",
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                article.description ?: "--",
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.body2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    val article = Article(
        title = "Lagos party plans threatened by Nigeria Covid passports",
        description = "Nigeria was briefly put on the UK's travel red list but most people are indifferent to coronavirus. Nigeria was briefly put on the UK's travel red list but most people are indifferent to coronavirus.",
        source = Source(id = "1", name = "bbc")
    )
    NewsRow(navController = navController, article = article, subject = "none")
}
