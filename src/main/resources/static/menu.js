var menu = (function () {
    let tableDisplayed = "container1";
    let tableNotDisplayed = "container2";

    class Menu {
        toggleTables = () => {
            document.getElementById(tableDisplayed).style.display = "block";
            document.getElementById(tableNotDisplayed).style.display = "none";

            [tableDisplayed, tableNotDisplayed] = [tableNotDisplayed, tableDisplayed]
        }
    }

    return new Menu();
})();