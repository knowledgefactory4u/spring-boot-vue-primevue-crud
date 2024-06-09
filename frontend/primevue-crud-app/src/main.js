import "primeflex/primeflex.css";
import "primevue/resources/themes/mdc-light-deeppurple/theme.css";
import "primevue/resources/primevue.min.css";
import "primeicons/primeicons.css";

import PrimeVue from "primevue/config";

import Button from 'primevue/button';
import Dialog from 'primevue/dialog';
import InputText from 'primevue/inputtext';

import DataTable from "primevue/datatable"
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';     
import Row from 'primevue/row';                     

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
const app = createApp(App)

app.use(router)
app.use(PrimeVue, { ripple: true })

app.component('DataTable', DataTable)
    .component('Column', Column)
    .component('ColumnGroup', ColumnGroup)
    .component('Row', Row)
    .component('Button', Button)
    .component('Dialog', Dialog)
    .component('InputText', InputText)


app.mount('#app')
