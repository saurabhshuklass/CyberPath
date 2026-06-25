package com.example.cyberpath.data

import com.example.cyberpath.models.EmailModel

object EmailRepository {

    val emails = listOf(

        EmailModel(

            sender = "security-alert@paypal-secure.net",

            subject = "⚠ Your account has been compromised!",

            body = """
Dear valued customer,

We detected suspicious activity in your PayPal account.

Your account will be suspended within 24 hours unless you verify your information immediately.

Click here:

http://paypal-verify-account.xyz/secure
            """.trimIndent(),

            isPhishing = true,

            explanation = listOf(
                "Fake domain: paypal-secure.net",
                "Urgent language creates panic",
                "Suspicious verification link",
                "Generic greeting"
            )
        ),

        EmailModel(

            sender = "newsletter@medium.com",

            subject = "Your Weekly Reading List is Ready",

            body = """
Hi Alex,

Here are your personalized story recommendations based on your interests.

Tap below to continue reading.

Thanks,
Medium Team
            """.trimIndent(),

            isPhishing = false,

            explanation = listOf(
                "Official domain medium.com",
                "No urgent requests",
                "Personalized content",
                "No suspicious links"
            )
        )

    )
}