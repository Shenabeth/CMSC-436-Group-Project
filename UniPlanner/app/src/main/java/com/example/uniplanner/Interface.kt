package com.example.uniplanner

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction
import com.example.uniplanner.RequestClass
import com.example.uniplanner.ResponseClass

interface MyInterface {
    /**
     * Invoke the Lambda function "AndroidBackendLambdaFunction".
     * The function name is the method name.
     */
    @LambdaFunction
    fun AndroidBackendLambdaFunction(request: RequestClass?): ResponseClass?
}