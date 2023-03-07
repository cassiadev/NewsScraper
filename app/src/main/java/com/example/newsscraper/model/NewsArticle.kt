package com.example.newsscraper.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class NewsArticle(
    val title: String,   //  <a href="https://qiita.com/KokumaruGarasu/items/93260136bc1f668db058" class="style-14xjz8f">自分で作ったAI同士を会話させてみた</a>
    val author: String,   // <p class="style-mpxsrl"> <a href="/KokumaruGarasu"> @<!-- --> KokumaruGarasu <span class="style-15fzge"> (<!-- --> kabuki<!-- -->) </span> </a> </p>
    val imageUrl: String,   // <a href="/KokumaruGarasu" class="style-j198x4"> <img alt="" class="style-1u747qi" height="32" loading="lazy" src="https://s3-ap-northeast-1.amazonaws.com/qiita-image-store/0/713689/a24ac4a3580ce506d675b608dee2cca4090bdd8c/x_large.png?1609824449" width="32"/> </a>
    val createdDate: String,    //  <span class="style-1elrt2j"><time dateTime="2023-02-06T02:53:57Z">2023年02月06日</time></span>
    val likes: Int, //  <span class="style-176d67y">47</span>
)