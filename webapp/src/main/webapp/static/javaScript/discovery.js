document.addEventListener("DOMContentLoaded", () => {

    const nextPageButton = document.getElementById("nextPageButton");
    if (nextPageButton != null) {
        nextPageButton.addEventListener("click", () => {
            document.getElementById("currentPageID").value = parseInt(document.getElementById("currentPageID").value) + 1
            submitFilters(event);
        });
    } else {
        document.getElementById("currentPageID").value = 1;
    }

    const previousPageButton = document.getElementById("previousPageButton");
    if (previousPageButton != null) {
        previousPageButton.addEventListener("click", () => {
            document.getElementById("currentPageID").value = parseInt(document.getElementById("currentPageID").value) - 1
            submitFilters(event);
        });
    } else {
        document.getElementById("currentPageID").value = 1;
    }

    const submitFilters = (event) => {
        event.preventDefault(); // Prevent the default form submission behavior
        let i = 0
        let j = 0

        for (const language of document.getElementsByClassName("languageLabel")) {
            if (document.getElementById("language-" + i).checked) {
                const value = document.getElementById("language-" + i + "-label").childNodes[0].textContent
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="languages[` + j + `]" id="languageId-` + j + `" value="` + value + `">`
                j++
            }
            i++
        }
        i = 0
        j = 0
        for (const physicalCondition of document.getElementsByClassName("physicalConditionLabel")) {
            const value = document.getElementById("physicalCondition-" + i + "-label").dataset.physicalcondition
            if (document.getElementById("physicalCondition-" + i).checked) {
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="physicalConditions[` + j + `]" id="physicalConditionId-` + j + `" value="` + value + `">`
                j++
            }
            i++
        }

        const search = document.getElementById("search-bar").value
        if (search !== "" && search != null) {
            document.getElementById("springForm").innerHTML += `<input type ="hidden" name="search" value="` + search + `">`
        }

        //document.getElementById("currentPageID").value = "1";

        document.getElementById("springForm").submit();
    }

    document.getElementById("submit-filter").addEventListener("click", submitFilters, true);

    document.getElementById("search-bar").addEventListener("keyup", (event) => {
        if (event.key === "Enter" || event.code === "Enter") {
            submitFilters(event);
        }
    })

    // Sorting buttons handlers
    document.getElementById("leastRecent").addEventListener("click", () => {
        document.getElementById("sort").value = "RECENT";
        document.getElementById("sortDirection").value = "ASCENDING";
        submitFilters(event);
    });

    document.getElementById("mostRecent").addEventListener("click", () => {
        document.getElementById("sort").value = "RECENT";
        document.getElementById("sortDirection").value = "DESCENDING";
        submitFilters(event);
    });

    document.getElementById("authorAscending").addEventListener("click", () => {
        document.getElementById("sort").value = "AUTHOR_NAME";
        document.getElementById("sortDirection").value = "ASCENDING";
        submitFilters(event);
    });

    document.getElementById("authorDescending").addEventListener("click", () => {
        document.getElementById("sort").value = "AUTHOR_NAME";
        document.getElementById("sortDirection").value = "DESCENDING";
        submitFilters(event);
    });

    document.getElementById("titleAscending").addEventListener("click", () => {
        document.getElementById("sort").value = "TITLE_NAME";
        document.getElementById("sortDirection").value = "ASCENDING";
        submitFilters(event);
    });

    document.getElementById("titleDescending").addEventListener("click", () => {
        document.getElementById("sort").value = "TITLE_NAME";
        document.getElementById("sortDirection").value = "DESCENDING";
        submitFilters(event);
    });

    // Get the range input element and label element
    var rangeInput = document.getElementById('customRange3');
    var rangeLabel = document.getElementById('customRange3Id');

    // Add an event listener to the range input
    rangeInput.addEventListener('input', function() {
        document.getElementById("minRating").value = rangeInput.value;
        var min = document.getElementById("minRating").value;
        var max = document.getElementById("maxRating").value;
        // Update the label text with the current value
        rangeLabel.textContent = min + "★" + " - " + max + "★";
    });

})