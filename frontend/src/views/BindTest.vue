<template>
    <h2>You select {{ selectedType }}!</h2>
    <div>
        <label>Food </label>
        <select @change="changeProduct" v-model="selectedType">
            <option>None</option>
            <option :key="food.idx" v-for="food in foodList">{{ food.name }}</option>
        </select>
        <br>
        <label>Product </label>
        <select>
            <option>None</option>
            <option :key="selectedProduct.idx" v-for="selectedProduct in selectedTypeList">
                {{ selectedProduct.name }}
            </option>
        </select>
    </div>
</template>

<script>
export default{
    data(){
        return {
            foodList: [
                { name: "Ramyeon", idx: 1 },
                { name: "Fish", idx: 2 },
                { name: "Snack", idx: 3 }
            ],
            productList: [
                { type: "Ramyeon", name: "Sin-ramyeon", idx: 1 },
                { type: "Ramyeon", name: "Jin-ramyeon", idx: 2 },
                { type: "Ramyeon", name: "Raccoon", idx: 3 },
                { type: "Fish", name: "Mackerel", idx: 4 },
                { type: "Fish", name: "Herring", idx: 5 },
                { type: "Fish", name: "Salmon", idx: 6 },
                { type: "Snack", name: "Diget", idx: 7 },
                { type: "Snack", name: "Homerunball", idx: 8 },
                { type: "Snack", name: "Pokachip", idx: 9 },
            ],
            selectedType: "",
            selectedTypeList : []
        }
    },
    methods: {
        changeProduct(){
            this.selectedTypeList = this.productList.filter((product) => product.type === this.selectedType)
        }
    }
}
</script>

<style>

</style>