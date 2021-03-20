package com.example.protodatastore.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.codelab.android.datastore.UserPreference
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer: Serializer<UserPreference> {
    override val defaultValue: UserPreference
        get() = UserPreference.getDefaultInstance()

    override fun readFrom(input: InputStream): UserPreference {
        try {
            return UserPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: UserPreference, output: OutputStream) {
        t.writeTo(output)
    }

}