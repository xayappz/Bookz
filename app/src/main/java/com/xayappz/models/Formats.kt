package com.xayappz.models

import com.google.gson.annotations.SerializedName

data class Formats(
    val application_epub_zip: String,
    val application_octet_stream: String,
    @SerializedName("application/rdf+xml")
    val application_rdf_xml: String,
    val application_x_mobipocket_ebook: String,
    @SerializedName("text/html")
    val text_html: String,
    val text_html_charset_iso_8859_1: String,
    val text_html_charset_us_ascii: String,
    val text_html_charset_utf_8: String,
    @SerializedName("text/plain; charset=utf-8")
    val text_plain: String,
    val text_plain_charset_iso_8859_1: String,
    val text_plain_charset_us_ascii: String,
    val text_plain_charset_utf_8: String,
    @SerializedName("image/jpeg")
    val image_jpeg: String,
    @SerializedName("application/zip")
    val zipurl: String
)