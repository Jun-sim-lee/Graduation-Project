<template>
    <div class = "screen">
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px"> &lt; 전체 리소스</button>
                <button class = "gear" @click="clickGear"></button>
            </div>
            <div :class="{'hidden_drawer': gearClick}">
                <ul class="drawer"
                    style="position: absolute; top: 0px; right: 30px; 
                           background-color: white; width: 100px; height: 100px; border-radius: 20px;">
                    <li class="drawer_list" v-bind:style="isNotSorted" style="border-bottom: 1px solid #808080;" @click="sortAll"> 전체 </li>
                    <li class="drawer_list" v-bind:style="isSortedMine" style="border-bottom: 1px solid #808080;" @click="sortMine"> 권한 내 리소스 </li>
                    <li class="drawer_list" v-bind:style="isSortedForbidden" @click="sortForbidden"> 권한 외 리소스 </li>
                </ul>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: serif;">All Resource</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px;">모든 리소스 목록입니다.<br>
                정렬을 통해 권한 별 리소스 확인이 가능합니다.</p>
        </div>
        <div style="width: 389px; height: 611px; background-color: white; overflow: scroll;">
            <ul>
                <template :key="resource.deviceName" v-for="resource in listForShow">
                    <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;">
                        <img v-if="!resource.on" src="../assets/redglow.png" style="width: 25px; height: 25px;">
                        <img v-if="resource.on" src="../assets/greenglow.png" style="width: 25px; height: 25px;">
                        <span style="font-size: 18px; line-height: 35px;">{{ resource.deviceName }}</span>
                        <button v-if="resource.auth == 'Student'" class="resource_button_ask" @click="requestAdd(resource.id)">추가</button>
                        <button v-if="resource.auth == 'Professor'" class="resource_button_ask" @click="requestPermission(resource.id)">요청</button>
                    </li>
                </template>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref, onMounted, inject} from 'vue'
import axios from 'axios';

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')

const gearClick = ref(true)
const listForShow = ref([])
const sortedMyResourceList = ref([])
const sortedForbiddenResourceList = ref([])
const isNotSorted = ref({color: 'red'});
const isSortedMine = ref();
const isSortedForbidden = ref();

const resourceList = ref([])
const addRequestDto = ref({
    resourceId: 0
})

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestResourceList();
})

function requestResourceList(){ // 기본적으로 전체 정렬
    axios.get(requestURL + "resources", {headers})
        .then((resp) => {
            resourceList.value = resp.data;
            listForShow.value = resourceList.value;
        })
}

// 여기서 부터 정렬 메서드
function sortAll(){
    isNotSorted.value = {color: "red"};
    isSortedMine.value = {color: "black"};
    isSortedForbidden.value = {color: "black"};

    listForShow.value = resourceList.value
}
function sortMine(){
    isNotSorted.value = {color: "black"};
    isSortedMine.value = {color: "red"};
    isSortedForbidden.value = {color: "black"};

    resourceList.value.forEach(object => {
        if(object['auth'] === "Student")
            sortedMyResourceList.value.push(object)
        // 추후에 권한을 현재 로그인 한 사람 권한으로 바꿔야함
        // 방식은 store.state.authority를 통해서!
    });

    listForShow.value = sortedMyResourceList.value
    sortedMyResourceList.value = []
}
function sortForbidden(){
    isNotSorted.value = {color: "black"};
    isSortedMine.value = {color: "black"};
    isSortedForbidden.value = {color: "red"};

    resourceList.value.forEach(object => {
        if(object['auth'] === "Professor")
            sortedForbiddenResourceList.value.push(object)
    });
    
    listForShow.value = sortedForbiddenResourceList.value
    sortedForbiddenResourceList.value = []
}
// 정렬 버튼 나오게
function clickGear(){
    gearClick.value = !gearClick.value
}

// 이전 화면으로
function moveToPrev(){
    router.go(-1)
}

// 여기서부터는 요청 메서드
function requestAdd(target){
    addRequestDto.value.resourceId = target // 리소스 아이디 받아옴
    axios.post(requestURL + "askResource", addRequestDto.value, {headers})
         .then((resp) => {
            if(resp.status === 200)
                alert("리소스 추가 요청이 전송 되었습니다.")
         })
         .catch((error) => {
            alert(error)
         })

    // 해당 요청에 들어갈 값은
    // 버튼을 눌렀을 때 해당 리소스 이름
}


function requestPermission(target){
    addRequestDto.value.resourceId = target // 리소스 아이디 받아옴
    // 권한 밖의 요청
    axios.post(requestURL + "askResource", addRequestDto.value, {headers})
         .then((resp) => {
            if(resp.status === 200)
                alert("리소스 추가 요청이 완료되었습니다.")
         })
         .catch((error) => {
            alert(error)
         })

    // 해당 요청에 들어갈 값은
    // 버튼을 눌렀을 때 해당 리소스 이름
}

</script>

<style>
@import "../style/wmistyle.css";
</style>