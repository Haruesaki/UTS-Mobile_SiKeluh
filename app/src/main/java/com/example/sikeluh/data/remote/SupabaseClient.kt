package com.example.sikeluh.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://lreczqvbvxyeuezvfkqk.supabase.co",
        supabaseKey = "sb_publishable_8L32MlLQIH-loDMCZ8xc2Q_ppXqNydA"
    ) {
        install(Postgrest)
    }
}
