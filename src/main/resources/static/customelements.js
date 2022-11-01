class UserTable extends HTMLElement{

    static get observedAttributes() { return ['users']; }

    constructor() {
        super();
        this._users = [];
        this._table = {};
    }

    connectedCallback() {
        let shadowRoot = this.attachShadow({ mode: "open" });

        // Apply external styles to the shadow DOM
        const linkElem = document.createElement("link");
        linkElem.setAttribute("rel", "stylesheet");
        linkElem.setAttribute("href", "https://cdn.jsdelivr.net/npm/purecss@2.1.0/build/pure-min.css");

// Attach the created element to the shadow DOM
        shadowRoot.appendChild(linkElem);
        this.table = document.createElement("table");
        this.table.classList.add("pure-table")

        let thead = document.createElement("thead");
        thead.appendChild(this.createTh("id"))
        thead.appendChild(this.createTh("First Name"))
        thead.appendChild(this.createTh("Last Name"))
        thead.appendChild(this.createTh("Email"))
        thead.appendChild(this.createTh("Address"))
        this.table.appendChild(thead)
        this.table.appendChild(document.createElement("tbody"))
        this.shadowRoot.appendChild(this.table)
    }

    get table(){
        return this._table;
    }

    set table(tab){
        this._table = tab;
    }

    get users() {
        return this._users;
    }

    set users(usersPassed) {
        this._users = [...usersPassed]

        let tbody = document.createElement("tbody");
        for(const user of this._users){
            let tr = document.createElement("tr");
            tbody.appendChild(tr);
            this.createCell(user, tr, "id");
            this.createCell(user, tr, "first_name");
            this.createCell(user, tr, "last_name");
            this.createCell(user, tr, "email");
            this.createCell(user, tr, "address");
        }
        this.table.removeChild(this.table.childNodes[1]);
        this.table.append(tbody);
    }

    createTh(text) {
        let thead = document.createElement("th");
        thead.innerText = text;
        return thead;
    }

    createCell(user, tr, prop) {
        let cell = document.createElement("td");
        cell.innerHTML = user[prop]
        tr.appendChild(cell);
    }
}

customElements.define("user-table", UserTable, {});

class DebouncedInput extends HTMLInputElement {

    static get observedAttributes() { return ['time']; }

    constructor(...args) {
        super(...args);
        this._time = 300;
        this.addEventListener("input", this.onInput);
    }

    debounce  (func, debounceTime) {
        let timeout;
        return function executedFunction(...args) {
            clearTimeout(timeout);

            timeout = setTimeout(() => {
                clearTimeout(timeout);
                func(...args);
            }, debounceTime);
        };
    };

    set time (time){
        this._time = time
    }

    get time () {
        return this._time;
    }

    onInput() {
        this.debounce(() => {
            console.log("added input" + this.value)
        }, this._time)();
    }
}

customElements.define("debounced-input", DebouncedInput, {extends: "input"});