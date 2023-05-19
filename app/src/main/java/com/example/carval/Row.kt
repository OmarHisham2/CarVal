package com.example.carval

data class Row(val name:String, val year:Int, val kilometers:Int, val fuelType:Int,
               val trans:Int, val ownerType:Int, val mileage:Float, val engine:Int,
               val power:Float, val seats:Int, val newPrice:Float, val price:Float
               ){
    override fun toString(): String {
        return "Row(name='$name', year=$year, kilometers=$kilometers, fuelType=$fuelType, " +
                "trans=$trans, ownerType=$ownerType, mileage=$mileage, engine=$engine, " +
                "power=$power, seats=$seats, newPrice=$newPrice, price=$price)"
    }
}
