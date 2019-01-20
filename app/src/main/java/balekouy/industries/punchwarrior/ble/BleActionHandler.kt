package balekouy.industries.punchwarrior.ble

interface BleActionHandler {
    fun handleReceveidValue(actionValue: Short)
}