<template>
  <dialog id="verificationAddModal" class="modal">
    <div method="dialog" class="modal-box">
      <div class="flex justify-between items-center mb-8">
        <h2 class="font-bold text-xl">질문 접근권한 추가</h2>
        <label class="btn btn-sm btn-circle btn-ghost" @click="toggleModal"
          >✕</label
        >
      </div>
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
            <option
              v-for="position in typeCategory"
              :key="position.positionId"
              :value="position.positionId"
            >
              {{ position.positionName }}
            </option>
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
            <option
              v-for="level in levelCategory"
              :key="level.questionLevelId"
              :value="level.questionLevelId"
            >
              {{ level.questionLevelName }}
            </option>
          </select>
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
              <span class="mr-2 text-primary">{{ item.levelName }}</span>
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
        <!--        {{ typeCategory[0].positionId }}-->
      </div>
    </div>
  </dialog>
</template>
<script setup>
import { ref, watch } from "vue";
import {
  getAccessVerification,
  getCategory,
  updateAccessVerfication,
} from "@/api/interviewer";

const userId = ref();
const levelNumber = ref("");
const positionName = ref("");

const accessBox = ref([]);
const requestBox = ref([]);
const typeCategory = ref([]);
const levelCategory = ref([]);

const addVerification = () => {
  if (!levelNumber.value || !positionName.value) {
    // 두 개의 input이 모두 입력되지 않은 경우에는 추가하지 않고 함수 종료
    return;
  }

  // 이미 추가된 접근 권한인지 확인
  const isDuplicate = accessBox.value.some(
    (item) =>
      item.levelId === levelNumber.value &&
      item.positionId === positionName.value,
  );

  if (isDuplicate) {
    // 이미 추가된 접근 권한이라면 alert 창을 띄우고 함수 종료
    alert("이미 추가된 접근 권한입니다.");
    // 입력된 값을 원상복구
    levelNumber.value = "";
    positionName.value = "";
    return;
  }

  const selectedType = typeCategory.value.find(
    (position) => position.positionId === positionName.value,
  );
  const levelIdValue = selectedType ? selectedType.positionId : null;

  const selectedLevel = levelCategory.value.find(
    (level) => level.questionLevelId === levelNumber.value,
  );
  const positionIdValue = selectedLevel ? selectedLevel.questionLevelId : null;

  const inputItem = {
    levelId: levelNumber.value,
    levelName: selectedLevel ? selectedLevel.questionLevelName : null,
    positionId: positionName.value,
    positionName: selectedType ? selectedType.positionName : null,
  };

  console.log(inputItem);
  accessBox.value.push(inputItem);
  levelNumber.value = "";
  positionName.value = "";
};

watch(
  [levelNumber, positionName],
  ([newLevel, newPosition], [oldLevel, oldPosition]) => {
    if (newLevel && newPosition) {
      addVerification();
    }
  },
);

const deleteVerification = (index) => {
  accessBox.value.splice(index, 1); // 해당 인덱스의 항목 삭제
};

const init = async (userId) => {
  const axiosResponse = await getAccessVerification(userId);
  console.log(axiosResponse.data.positionIdAndLevelIds);

  if (axiosResponse.status >= 200 && axiosResponse.status < 300) {
    const accessVerificationData = axiosResponse.data.positionIdAndLevelIds;
    accessBox.value = accessVerificationData.map((item) => ({
      levelId: item.levelId,
      levelName: item.levelName,
      positionId: item.positionId,
      positionName: item.positionName,
    }));

    console.log(accessBox.value);
  }

  const categories = await getCategory();
  typeCategory.value = categories.data.positionList;
  levelCategory.value = categories.data.questionLevelList;
  console.log(typeCategory);
  console.log(levelCategory);
};

const toggleModal = async (id) => {
  userId.value = id;
  console.log(userId.value);
  document
    .getElementById("verificationAddModal")
    .classList.toggle("modal-open");
  await init(id);
};

const permitBtn = async () => {
  console.log("in permitBtn", userId.value);
  const requestData = {
    positionAndLevelInfos: accessBox.value,
    userId: userId.value,
  };
  console.log("requestData: ", requestData);
  try {
    const axiosResponse = await updateAccessVerfication(requestData);

    if (axiosResponse.status >= 200 && axiosResponse.status < 300) {
      accessBox.value = [];
      requestBox.value = [];
      alert("등록이 완료되었습니다.");
      toggleModal(userId.value);
    } else {
      alert("등록에 실패했습니다. 다시 시도해주세요.");
    }
  } catch (error) {
    // 요청이 실패한 경우 에러 처리
    console.error("요청 실패:", error);
    alert("등록에 실패했습니다. 다시 시도해주세요.");
  }
};

defineExpose({
  toggleModal,
});
</script>