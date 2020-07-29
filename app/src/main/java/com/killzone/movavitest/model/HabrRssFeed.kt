package com.killzone.movavitest.model

import com.killzone.movavitest.util.extractImage
import com.killzone.movavitest.util.extractSymbols
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed(
    @field:Element
    var channel: RssChannel = RssChannel()
) {
    fun convert(): ConvertedHabrRssFeed {
        return ConvertedHabrRssFeed(
            ConvertedHabrChannel(
                channel.title,
                channel.link,
                channel.image.title,
                channel.image.url,
                channel.image.link
            ),
            channel.items.map { rssItem ->
                ConvertedHabrPost(
                    rssItem.guid,
                    rssItem.title,
                    extractSymbols(rssItem.description),
                    extractImage(rssItem.description),
                    rssItem.link,
                    rssItem.pubDate,
                    rssItem.creator,
                    rssItem.categories
                )
            }.toMutableList()
        )
    }
}


@Root(name = "channel", strict = false)
data class RssChannel(

    @field:Element
    var title: String = "",

    @field:Element
    var link: String = "",

    @field:Element
    var image: RssImage = RssImage(),

    @field:ElementList(entry = "item", inline = true, required = false)
    var items: MutableList<RssItem> = mutableListOf()
)

@Root(name = "image", strict = false)
data class RssImage(

    @field:Element
    var title: String = "",

    @field:Element
    var url: String = "",

    @field:Element
    var link: String = ""
)

@Root(name = "item", strict = false)
data class RssItem(

    @field:Element
    var guid: String = "",

    @field:Element
    var title: String = "",

   /* @field:Element
    var thumbnail: String = "",*/

    @field:Element
    var description: String = "",

    @field:Element
    var link: String = "",

    @field:Element
    var pubDate: String = "",

    @field:Element
    var creator: String = "",

    @field:ElementList(entry = "category", inline = true, required = false)
    var categories: MutableList<String> = mutableListOf()

)

