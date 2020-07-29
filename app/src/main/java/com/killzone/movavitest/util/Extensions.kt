package com.killzone.movavitest.util

import android.text.style.ImageSpan
import android.webkit.URLUtil
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import java.util.regex.Pattern


fun extractSymbols(text: String): String {
    val newText = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        .toString()
    return newText
}

fun extractImage(text: String): String? {
    val imageUrl = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        .getSpans<ImageSpan>()
        .map { it.source }
        .firstOrNull { URLUtil.isValidUrl(it) }

    return imageUrl
}

