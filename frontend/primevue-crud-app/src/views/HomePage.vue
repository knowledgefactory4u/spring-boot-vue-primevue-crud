<template>
  <div>
    <Dialog header="Add Todo" v-model:visible="addDialog" 
      :breakpoints="{'960px': '75vw', '640px': '90vw'}"
      :style="{width: '50vw'}" :modal="true">

      <TodoForm ref="addTodoFormRef" :forminp="addTodoInput">
        <template></template>
      </TodoForm>

      <template #footer>
        <Button label="Cancel" icon="pi pi-ban" @click="toggleAddDialog()" 
         class="p-button-text" style="margin-right:10px"></Button>
        <Button label="Save" icon="pi pi-check" @click="addTodoBtn()" autofocus></Button>
      </template>

    </Dialog>
  </div>
  <Dialog header="Edit Todo" v-model:visible="editDialog" 
   :breakpoints="{'960px': '75vw', '640px': '90vw'}"
    :style="{width: '50vw'}" :modal="true">

    <TodoForm ref="editTodoFormRef" :forminp="editTodoInput">
      <template></template>
    </TodoForm>

    <template #footer>
      <Button label="Cancel" icon="pi pi-ban" @click="hideEditDialog()" 
        class="p-button-text" style="margin-right:10px"></Button>
      <Button label="Edit" icon="pi pi-check" @click="editTodoBtn()" autofocus></Button>
    </template>
  </Dialog>

  <div>
    <DataTable :value="todos" :paginator="true" :rows="4" dataKey="id" 
      v-model:selection="selectedTodos" removableSort
      v-model:filters="filters" filterDisplay="row" :globalFilterFields="getColumnFields()">
      <template #header>
        <div class="flex justify-content-end">
          <span class="p-input-icon-left ">
            <i class="pi pi-search" />
            <InputText v-model="filters['global'].value" placeholder="Search" />
          </span>
        </div>
      </template>
      <Column v-for="col of columns" :field="col.field" filterField="col.field" 
        :header="col.header" :key="col.field"
        :sortable="true">
      </Column>
      <Column field="editor" header="Actions">
        <template #body="slotProps">
          <Button style="margin-right:10px" icon="pi pi-pencil" 
           class="p-button-rounded p-button-secondary"
            @click="showEditDialog(slotProps.data)"></Button>
          <Button icon="pi pi-trash" class="p-button-rounded p-button-danger"
            @click="removeTodo(slotProps.data)"></Button>
        </template>
      </Column>
    </DataTable>
  </div>
  <div>
    <Button style = "padding: 37px 37px;" title="Add Todo" icon="pi pi-plus-circle" 
      class="p-button-rounded" @click="toggleAddDialog()"></Button>
  </div>
</template>

<script setup>
//imports
import { getAllTodos, deleteTodo } from './services/todoservice.js';
import { ref, onMounted, reactive } from 'vue';
import TodoForm from '../components/TodoForm.vue';
import { FilterMatchMode } from 'primevue/api';
// columns
const columns = ref([
  { field: 'firstName', header: 'First-Name' },
  { field: 'lastName', header: 'Last-Name' },
  { field: 'email', header: 'Email' },
  { field: 'gender', header: 'Gender' }
]);
//vars
const addTodoFormRef = ref(null);
const editTodoFormRef = ref(null);
const selectedTodos = ref();
const todos = ref([]);
const editTodoInput = reactive({
  firstName: '',
  lastName: '',
  email: '',
  gender: ''
});
const addTodoInput = reactive({
  firstName: '',
  lastName: '',
  email: '',
  gender: ''
});
const editDialog = ref(false);
const addDialog = ref(false);

//filter stuff
const filters = ref({
  'global': { value: null, matchMode: FilterMatchMode.CONTAINS },
  'firstName': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
  'lastName': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
  'email': { value: null, matchMode: FilterMatchMode.CONTAINS },
  'gender': { value: null, matchMode: FilterMatchMode.EQUALS }
});
//helper and button functions
function getColumnFields() {
  let colfields = [];
  for (const element of columns.value) {
    colfields.push(element.field);
  }
  return colfields;
}
function updateTableContent() {
  getAllTodos().then(data => (todos.value = data));
}

function toggleAddDialog() {
  addDialog.value = !addDialog.value;
}

function showEditDialog(data) {
  editTodoInput.id = data.id;
  editTodoInput.firstName = data.firstName;
  editTodoInput.lastName = data.lastName;
  editTodoInput.email = data.email;
  editTodoInput.gender = data.gender;
  //
  editDialog.value = !editDialog.value;
}
function hideEditDialog() {
  editDialog.value = !editDialog.value;
}

async function removeTodo(data) {
 
  await deleteTodo(data);
  updateTableContent();
}

async function editTodoBtn() {
  const status = await editTodoFormRef.value.handleEditEvent();
  if (status) {
    hideEditDialog();
    await updateTableContent();
  }
}
async function addTodoBtn() {
  const status = await addTodoFormRef.value.handleAddEvent();
  if (status) {
    toggleAddDialog();
    await updateTableContent();
  }
}
onMounted(() => {
  getAllTodos().then(data => (todos.value = data));
})

</script>