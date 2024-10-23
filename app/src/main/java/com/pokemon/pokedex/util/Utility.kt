package com.pokemon.pokedex.util

class Utility {

    companion object {
        /**
         * get formatted id
         */
        fun getFormattedId(id: Int?): String =
            if (id != null) {
                if (id >= 10) id.toString()
                else "00$id"
            } else ""

        fun String.toUpperFirstChar(): String {
            if (this.isEmpty())
                return ""
            return this.replaceFirstChar { it.uppercase() }
        }
    }
}

