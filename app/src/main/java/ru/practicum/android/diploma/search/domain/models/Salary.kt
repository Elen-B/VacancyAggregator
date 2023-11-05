package ru.practicum.android.diploma.search.domain.models

data class Salary(
    val from: String?,
    val to: String?,
    val currency: CurrencyType?,
    val gross: Boolean = false
){

    fun getSalaryToTextView(): String{
       val text = StringBuilder()
       if(from.isNullOrBlank() && to.isNullOrBlank()){
           text.append("Зарплата не указана")
       }
        else if(from.isNullOrBlank()){
           text.append("до $to ${currency?.name}")
        }
       else if(to.isNullOrBlank()){
           text.append("от $from ${currency?.name}")
       }
        else{
           text.append("от $from до $to ${currency?.name}")
       }
        return text.toString()
    }
}

enum class CurrencyType {
    RUR, BYR, USD, EUR, KZT, UAH, AZN, UZS, GEL, KGT
}

/*
    Российский рубль (RUR / RUB)
    Белорусский рубль (BYR)
    Доллар (USD)
    Евро (EUR)
    Казахстанский тенге (KZT)
    Украинская гривна (UAH)
    Азербайджанский манат (AZN)
    Узбекский сум (UZS)
    Грузинский лари (GEL)
    Киргизский сом (KGT)
*/