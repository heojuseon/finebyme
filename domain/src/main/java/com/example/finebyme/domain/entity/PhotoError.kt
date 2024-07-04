package com.example.finebyme.domain.entity

//sealed class PhotoErrorAPI{
//
//    companion object {
//        const val UNAUTHORIZED_MESSAGE = "권한 오류"
//        const val LIMIT_EXCEEDED_MESSAGE = "요청 초과"
//    }
//
//
//    //권한 오류
//    data class UNAUTHORIZED(val message: String = UNAUTHORIZED_MESSAGE): PhotoErrorAPI()
//
//    //요청 초과
//    data class LIMITEXCEEDED(val message: String = LIMIT_EXCEEDED_MESSAGE): PhotoErrorAPI()
//}


enum class PhotoError(val message: String) {
    UNAUTHORIZED("권한 오류"),
    LIMITEXCEEDED("요청 초과")
}