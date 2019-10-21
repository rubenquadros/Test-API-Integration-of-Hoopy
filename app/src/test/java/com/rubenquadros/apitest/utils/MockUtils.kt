package com.rubenquadros.apitest.utils

import com.rubenquadros.apitest.data.model.fetch.Datum
import com.rubenquadros.apitest.data.model.fetch.FetchRequest
import com.rubenquadros.apitest.data.model.fetch.FetchResponse
import com.rubenquadros.apitest.data.model.insert.Data
import com.rubenquadros.apitest.data.model.insert.InsertRequest
import com.rubenquadros.apitest.data.model.insert.InsertResponse
import com.rubenquadros.apitest.data.model.metadata.Metadata
import com.rubenquadros.apitest.data.model.update.UpdateRequest
import com.rubenquadros.apitest.data.model.update.UpdateResponse

class MockUtils {

    companion object {
        fun getInsertRequest(): InsertRequest {
            val insertRequest = InsertRequest()
            insertRequest.name = "Ruben"
            insertRequest.email = "rquadros95@gmail.com"
            insertRequest.username = "ruben"
            insertRequest.contact = "8762551753"
            insertRequest.imageUrl = "https://ticketpicuploads.sgp1.digitaloceanspaces.com/media/1566988484006-IMG-20190828-WA0002.jpg"
            return insertRequest
        }

        fun getInsertResponse(): InsertResponse {
           return mockInsertResponse()
        }

        private fun mockInsertResponse(): InsertResponse {
            val insertResponse = InsertResponse()
            insertResponse.data = mockInsertData()
            insertResponse.metadata = mockInsertMetaData()
            return insertResponse
        }

        private fun mockInsertData(): Data {
            val data = Data()
            data.name = "Ruben"
            data.id = 12L
            data.username = "ruben"
            data.contact = "8762551753"
            data.email = "rquadros95@gmail.com"
            data.imageUrl = "https://ticketpicuploads.sgp1.digitaloceanspaces.com/media/1566988484006-IMG-20190828-WA0002.jpg"
            return data
        }

        private fun mockInsertMetaData(): Metadata {
            val metadata = Metadata()
            metadata.responseCode = 200L
            metadata.responseText = "User Data Inserted"
            return metadata
        }

        fun getFetchRequest(): FetchRequest {
            val fetchRequest = FetchRequest()
            fetchRequest.email = "rbn_quadros@rdiffmail.com"
            return fetchRequest
        }

        fun getFetchDataResponse(): FetchResponse {
            return mockFetchDataResponse()
        }

        private fun mockFetchDataResponse(): FetchResponse {
            val fetchResponse = FetchResponse()
            fetchResponse.data = mockFetchData()
            fetchResponse.metadata = mockFetchMetaData()
            return fetchResponse
        }

        private fun mockFetchData(): List<Datum> {
            val mList = ArrayList<Datum>()

            val datum = Datum()
            datum.id = 111
            datum.name = "Ruben"
            datum.contact = 8762551753
            datum.username = "ruben"
            datum.imageUrl = "https://ticketpicuploads.sgp1.digitaloceanspaces.com/media/1566988484006-IMG-20190828-WA0002.jpg"

            mList.add(datum)

            return mList
        }

        private fun mockFetchMetaData(): Metadata {
            val metadata = Metadata()
            metadata.responseCode = 200L
            metadata.responseText = "Data Query for users by username success"
            return metadata
        }

        fun getUpdateRequest(): UpdateRequest {
            val updateRequest = UpdateRequest()
            updateRequest.id = 111
            updateRequest.name = "Ruben Quadros"
            updateRequest.email = "rbn_quadros@rediffmail.com"
            updateRequest.contact = "8762551753"
            updateRequest.username = "ruben"
            return updateRequest
        }

        fun getUpdateResponse(): UpdateResponse {
            return mockUpdateResponse()
        }

        private fun mockUpdateResponse(): UpdateResponse {
            val updateResponse = UpdateResponse()
            updateResponse.data = mockUpdateData()
            updateResponse.metadata = mockUpdateMetaData()
            return updateResponse
        }

        private fun mockUpdateData(): List<com.rubenquadros.apitest.data.model.update.Datum> {
            val mList = ArrayList<com.rubenquadros.apitest.data.model.update.Datum>()

            val datum = com.rubenquadros.apitest.data.model.update.Datum()
            datum.contact = 8762551753
            datum.email = "rbn_quadros@rediffmail.com"
            datum.id = 111
            datum.username = "ruben"
            datum.name = "Ruben Quadros"

            mList.add(datum)

            return mList
        }

        private fun mockUpdateMetaData(): Metadata {
            val metadata = Metadata()
            metadata.responseCode = 200
            metadata.responseText = "Data Query for users by username success"
            return metadata
        }
    }
}