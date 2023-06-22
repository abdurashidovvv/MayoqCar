package com.example.mayoqcar.models

class Worker {

    var id:String?=null
    var login:String?=null
    var parol:String?=null
    var fullName:String?=null
    var job:String?=null
    var job_address:String?=null
    var medical_history:String?=null
    var position:String?=null
    var imageLink:String?=null
    var phoneNumber:String?=null
    var car_number:String?=null
    var lat:String?=null
    var long:String?=null

    constructor(
        id: String?,
        login: String?,
        parol: String?,
        fullName: String?,
        job: String?,
        job_address: String?,
        medical_history: String?,
        position: String?,
        imageLink: String?,
        phoneNumber: String?,
        car_number: String?,
        lat: String?,
        long: String?
    ) {
        this.id = id
        this.login = login
        this.parol = parol
        this.fullName = fullName
        this.job = job
        this.job_address = job_address
        this.medical_history = medical_history
        this.position = position
        this.imageLink = imageLink
        this.phoneNumber = phoneNumber
        this.car_number = car_number
        this.lat = lat
        this.long = long
    }

    constructor()

    override fun toString(): String {
        return "Worker(id=$id, login=$login, parol=$parol, fullName=$fullName, job=$job, job_address=$job_address, medical_history=$medical_history, position=$position, imageLink=$imageLink, phoneNumber=$phoneNumber, car_number=$car_number, lat=$lat, long=$long)"
    }


}