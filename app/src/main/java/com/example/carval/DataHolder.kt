import com.example.carval.Row

object DataHolder {
    lateinit var data: ArrayList<Row>

    fun init(data: ArrayList<Row>) {
        this.data = data
    }
}