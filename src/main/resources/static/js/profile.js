import {initializeTabs} from './profile/tabs.js';
import {initializeAvatar} from './profile/avatar.js';
import {initializeEditField} from './profile/profile-fields.js';
import {updateFieldOnServer} from './profile/profile-updates.js';

initializeTabs();
initializeAvatar();

const fields = [
    {
        input: document.getElementById('nameInput'),
        editBtn: document.getElementById('editNameBtn'),
        error: document.getElementById('nameError'),
        regex: /^[A-Za-zА-Яа-я\s-]+$/,
        fieldId: 'name'
    },
    {
        input: document.getElementById('emailInput'),
        editBtn: document.getElementById('editEmailBtn'),
        error: document.getElementById('emailError'),
        regex: /^[A-Za-z0-9]+@[A-Za-z]+\.[A-Za-z]+$/,
        fieldId: 'mail'
    },
    {
        input: document.getElementById('numberInput'),
        editBtn: document.getElementById('editNumberBtn'),
        error: document.getElementById('numberError'),
        regex: /\S/,
        fieldId: 'number'
    },
];

fields.forEach(({input, editBtn, error, regex, fieldId}) => {
    initializeEditField(input, editBtn, regex, error, (userId, newValue) => {
        updateFieldOnServer(userId, fieldId, newValue);
    });
});