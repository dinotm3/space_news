package dt.mesaric.spacenews.data.parser

import android.content.Context
import dt.mesaric.spacenews.data.factory.createGetHttpUrlConnection
import dt.mesaric.spacenews.data.factory.createXMLPullParser
import dt.mesaric.spacenews.data.handlers.downloadImageAndStore
import dt.mesaric.spacenews.domain.model.Article
import org.xmlpull.v1.XmlPullParser
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val RSS_URL = "https://www.space.com/feeds/all"
private const val ATTRIBUTE_URL = "url"
private const val FILE_PREFIX = "ARTICLE_"


fun parse(context: Context) : List<Article> {
    var articles = mutableListOf<Article>()
    val con = createGetHttpUrlConnection(RSS_URL)

    con.inputStream.use {

        var parser = createXMLPullParser(it)
        var tagType:TagType? = null
        var article:Article? = null

        var event = parser.eventType
        while(event != XmlPullParser.END_DOCUMENT){
            when(event) {
                XmlPullParser.START_TAG -> {
                    var name = parser.name
                    tagType = try {
                        TagType.of(name)
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                    if (article != null && TagType.ENCLOSURE == tagType){
                        val url = parser.getAttributeValue(null, ATTRIBUTE_URL)
                        if (url != null){
                            val picturePath =
                                downloadImageAndStore(
                                context, url, FILE_PREFIX + article.title.hashCode())
                            if (picturePath != null) {
                                article.picturePath = picturePath
                            }
                        }
                    }
                }
                XmlPullParser.TEXT -> {
                    if (tagType != null) {
                        val text = parser.text.trim()
                        when(tagType) {
                            TagType.ITEM -> {
                                article = Article()
                                articles.add(article)
                            }
                            TagType.TITLE -> {
                                if (article != null && text.isNotBlank()){
                                    article.title = text
                                }
                            }
                            TagType.DESCRIPTION -> {
                                if (article != null && text.isNotBlank()){
                                    article.description = text
                                }
                            }
                            TagType.PUBDATE -> {
                                if (article != null && text.isNotBlank()){
                                    article.publishedDateTime =
                                        LocalDateTime.parse(
                                            text, DateTimeFormatter.RFC_1123_DATE_TIME)
                                }
                            }
                        }
                    }
                }
            }
            event = parser.next()
        }
    }
    return articles
}

private enum class TagType (val value: String){
    ITEM("item"),
    TITLE("title"),
    DESCRIPTION("description"),
    PUBDATE("pubDate"),
    ENCLOSURE("enclosure");

    companion object {
        fun of(value:String) = valueOf(value.uppercase(Locale.getDefault()))
    }
}