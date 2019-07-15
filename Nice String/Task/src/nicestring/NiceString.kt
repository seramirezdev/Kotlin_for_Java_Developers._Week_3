package nicestring

fun String.isNice(): Boolean {

    val vowels = this.count { it == 'a' || it == 'e' || it == 'i' || it == 'o' || it == 'u' } >= 3

    val unions = this.zipWithNext()

    val containsSub = !contains("ba") && !contains("be") && !contains("bu")

    val containsDoubleLetter = unions.any { it.first == it.second }

    var conditions = 0

    if (vowels) conditions++
    if (!containsSub) conditions++
    if (containsDoubleLetter) conditions++

    return conditions >= 2
}

fun String.otherIsNice(): Boolean {

    val noBadSubstring = setOf("ba", "be", "bu").none { this.contains(it) }
    val hasThreeVowel = count { it in "aeiou" } >= 3
    val hasDouble = zipWithNext().any { it.first == it.second }

    val conditions = listOf(noBadSubstring, hasThreeVowel, hasDouble).filter { it }.size
    return conditions >= 2
}


fun main () {
    val s = "abcddd"

    println(s.otherIsNice())
}