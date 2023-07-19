<template>
  <dialog id="verificationModal" class="modal">
    <div method="dialog" class="modal-box p-10">
      <h2 class="mb-8 text-xl font-semibold tracking-tight">질문 접근 권한</h2>
      <div class="flex justify-between w-full">
        <div class="w-1/2">
          <label
            for="position"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >직무</label
          >
          <select
            class="font-medium w-[90%] select select-primary select-sm border-gray-300"
            v-model="positionName"
          >
            <option value="프론트">프론트</option>
            <option value="백엔드">백엔드</option>
          </select>
        </div>
        <div class="w-1/2">
          <label
            for="level"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >난이도</label
          >
          <select
            class="w-[90%] font-medium select select-primary select-sm border-gray-300"
            v-model="levelNumber"
          >
            <option value="상">상</option>
            <option value="중">중</option>
            <option value="하">하</option>
          </select>
        </div>
        <div
          class="flex justify-center items-end mb-2 cursor-pointer"
          @click="addVerification"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M12 4.5v15m7.5-7.5h-15"
            />
          </svg>
        </div>
      </div>

      <p class="border-t-gray-300 border-t mt-8 pt-8 font-bold mb-5">
        추가된 접근 권한
      </p>
      <div class="accessBox">
        <div v-for="(item, index) in accessBox" :key="index" class="mb-2">
          <div class="flex justify-between">
            <div>
              <span class="mr-2">{{ index + 1 }}.</span>
              <span class="text-primary mr-2">{{ item.positionName }}</span>
              <span class="mr-2 text-primary">{{ item.levelNumber }}</span>
            </div>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6 cursor-pointer"
              @click="deleteVerification(index)"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M18 12H6"
              />
            </svg>
          </div>
        </div>
      </div>
      <div class="flex justify-end mt-12">
        <button
          class="flex flex-col mr-3 py-5 px-7 btn btn-sm btn-primary btn-outline"
          type="button"
          @click="toggleModal"
        >
          취소
        </button>
        <button
          class="flex flex-col py-5 px-7 btn btn-sm btn-primary"
          type="button"
          @click="permitBtn"
        >
          허용
        </button>
      </div>
    </div>
  </dialog>
</template>
<script setup>
import { ref } from "vue";
import { getAccessVerification } from "@/api/interviewer";

const userId = ref(null);
const levelId = ref(null);
const levelNumber = ref("");
const positionId = ref(null);
const positionName = ref("");
const accessBox = ref([]);

const addVerification = () => {
  if (!levelNumber.value || !positionName.value) {
    alert("직무와 난이도 모두를 입력 해주세요.");
    return;
  }
  const inputItem = {
    levelNumber: levelNumber.value, // select의 입력값
    positionName: positionName.value, // select의 입력값
  };
  accessBox.value.push(inputItem);
  levelNumber.value = "";
  positionName.value = "";
};

const deleteVerification = (index) => {
  accessBox.value.splice(index, 1); // 해당 인덱스의 항목 삭제
};

const init = async (userId) => {
  const axiosResponse = await getAccessVerification(userId);
  console.log("wekgjwekgj wejglw", axiosResponse.data.positionIdAndLevelIds);
};

const toggleModal = async (id) => {
  userId.value = id;
  document.getElementById("verificationModal").classList.toggle("modal-open");
  await init(id);
};

const permitBtn = async () => {
  const requestData = {
    positionAndLevelInfos: [
      {
        levelId: 0,
        levelName: "string",
        positionId: 0,
        positionName: "string",
      },
    ],
    userId: userId.value,
  };

  console.log(requestData);
  // const axiosResponse = await updateAccessVerfication(requestData);
};

const deleteinterviewerBtn = () => {};

defineExpose({
  toggleModal,
});
</script>
