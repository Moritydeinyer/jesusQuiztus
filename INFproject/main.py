import psycopg2
from psycopg2 import sql

# Fragen und Antworten im JSON-Format
questions_data = [
    {
        "question": "Which planet is known as the Red Planet?",
        "answers": ["Earth", "Mars", "Jupiter", "Saturn"],
        "correct": "Mars"
    },
    {
        "question": "What is the largest ocean on Earth?",
        "answers": ["Atlantic Ocean", "Pacific Ocean", "Indian Ocean", "Arctic Ocean"],
        "correct": "Pacific Ocean"
    },
    {
        "question": "Which element has the chemical symbol 'O'?",
        "answers": ["Gold", "Oxygen", "Hydrogen", "Carbon"],
        "correct": "Oxygen"
    },
    {
        "question": "Who wrote the play 'Romeo and Juliet'?",
        "answers": ["William Shakespeare", "Charles Dickens", "Mark Twain", "Jane Austen"],
        "correct": "William Shakespeare"
    },
    {
        "question": "Which country is the largest by land area?",
        "answers": ["Canada", "China", "Russia", "United States"],
        "correct": "Russia"
    },
    {
        "question": "What is the capital city of Japan?",
        "answers": ["Beijing", "Seoul", "Tokyo", "Bangkok"],
        "correct": "Tokyo"
    },
    {
        "question": "How many continents are there on Earth?",
        "answers": ["7", "5", "6", "8"],
        "correct": "7"
    },
    {
        "question": "Which year did the Titanic sink?",
        "answers": ["1900", "1915", "1912", "1920"],
        "correct": "1912"
    },
    {
        "question": "Estimate the approximate population of the world as of 2023.",
        "answers": ["5 billion", "8 billion", "10 billion", "12 billion"],
        "correct": "8 billion"
    },
    {
        "question": "What is the smallest country in the world by area?",
        "answers": ["Monaco", "Vatican City", "San Marino", "Liechtenstein"],
        "correct": "Vatican City"
    },
    {
        "question": "Who painted the Mona Lisa?",
        "answers": ["Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"],
        "correct": "Leonardo da Vinci"
    },
    {
        "question": "Which is the longest river in the world?",
        "answers": ["Amazon River", "Yangtze River", "Nile River", "Mississippi River"],
        "correct": "Nile River"
    },
    {
        "question": "What is the capital city of Australia?",
        "answers": ["Sydney", "Melbourne", "Canberra", "Brisbane"],
        "correct": "Canberra"
    },
    {
        "question": "Who was the first person to walk on the moon?",
        "answers": ["Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "Michael Collins"],
        "correct": "Neil Armstrong"
    },
    {
        "question": "Which country gifted the Statue of Liberty to the United States?",
        "answers": ["Germany", "Canada", "Spain", "France"],
        "correct": "France"
    },
    {
        "question": "Which language has the most native speakers worldwide?",
        "answers": ["English", "Spanish", "Mandarin Chinese", "Hindi"],
        "correct": "Mandarin Chinese"
    },
    {
        "question": "How many time zones does Russia span?",
        "answers": ["8", "9", "11", "12"],
        "correct": "11"
    },
    {
        "question": "What is the capital of Canada?",
        "answers": ["Toronto", "Vancouver", "Ottawa", "Montreal"],
        "correct": "Ottawa"
    },
    {
        "question": "Which is the highest mountain in the world?",
        "answers": ["Mount Everest", "K2", "Kangchenjunga", "Lhotse"],
        "correct": "Mount Everest"
    },
    {
        "question": "What is the smallest planet in our solar system?",
        "answers": ["Mars", "Mercury", "Venus", "Pluto"],
        "correct": "Mercury"
    },
    {
        "question": "Which country is known as the Land of the Rising Sun?",
        "answers": ["China", "Thailand", "Japan", "South Korea"],
        "correct": "Japan"
    },
    {
        "question": "Which ocean is the Bermuda Triangle located in?",
        "answers": ["Indian Ocean", "Pacific Ocean", "Atlantic Ocean", "Southern Ocean"],
        "correct": "Atlantic Ocean"
    },
    {
        "question": "What is the most spoken language in South America?",
        "answers": ["English", "Spanish", "Portuguese", "French"],
        "correct": "Portuguese"
    },
    {
        "question": "Who developed the theory of relativity?",
        "answers": ["Isaac Newton", "Nikola Tesla", "Albert Einstein", "Galileo Galilei"],
        "correct": "Albert Einstein"
    },
    {
        "question": "Which is the hardest natural substance on Earth?",
        "answers": ["Gold", "Diamond", "Steel", "Iron"],
        "correct": "Diamond"
    },
    {
        "question": "What is the capital city of Italy?",
        "answers": ["Venice", "Milan", "Rome", "Naples"],
        "correct": "Rome"
    },
    {
        "question": "Which vitamin is known as the sunshine vitamin?",
        "answers": ["Vitamin A", "Vitamin C", "Vitamin D", "Vitamin E"],
        "correct": "Vitamin D"
    },
    {
        "question": "What is the currency of Japan?",
        "answers": ["Yuan", "Won", "Yen", "Ringgit"],
        "correct": "Yen"
    },
    {
        "question": "Who wrote 'Pride and Prejudice'?",
        "answers": ["Emily Brontë", "Charles Dickens", "Mark Twain", "Jane Austen"],
        "correct": "Jane Austen"
    },
    {
        "question": "Which planet is closest to the sun?",
        "answers": ["Earth", "Venus", "Mercury", "Mars"],
        "correct": "Mercury"
    },
    {
        "question": "What is the main ingredient in traditional Japanese miso soup?",
        "answers": ["Seaweed", "Tofu", "Miso paste", "Soy sauce"],
        "correct": "Miso paste"
    },
    {
        "question": "How many bones are in the human body?",
        "answers": ["204", "206", "208", "210"],
        "correct": "206"
    },
    {
        "question": "Who was the first President of the United States?",
        "answers": ["Abraham Lincoln", "Thomas Jefferson", "George Washington", "John Adams"],
        "correct": "George Washington"
    },
    {
        "question": "Which is the largest desert in the world?",
        "answers": ["Sahara Desert", "Antarctic Desert", "Arabian Desert", "Gobi Desert"],
        "correct": "Antarctic Desert"
    },
    {
        "question": "What is the chemical formula for water?",
        "answers": ["CO2", "H2O", "O2", "H2"],
        "correct": "H2O"
    },
    {
        "question": "Which country is home to the kangaroo?",
        "answers": ["South Africa", "New Zealand", "Australia", "India"],
        "correct": "Australia"
    },
    {
        "question": "Who discovered penicillin?",
        "answers": ["Alexander Fleming", "Marie Curie", "Louis Pasteur", "Gregor Mendel"],
        "correct": "Alexander Fleming"
    },
    {
        "question": "Which city is known as the Big Apple?",
        "answers": ["Los Angeles", "Chicago", "San Francisco", "New York City"],
        "correct": "New York City"
    },
    {
        "question": "What is the hardest rock?",
        "answers": ["Marble", "Granite", "Diamond", "Limestone"],
        "correct": "Diamond"
    },
    {
        "question": "Which planet is known for its rings?",
        "answers": ["Jupiter", "Uranus", "Neptune", "Saturn"],
        "correct": "Saturn"
    },
    {
        "question": "Who was the British Prime Minister during World War II?",
        "answers": ["Neville Chamberlain", "Winston Churchill", "Clement Attlee", "Harold Macmillan"],
        "correct": "Winston Churchill"
    },
    {
        "question": "Which natural disaster is measured with a Richter scale?",
        "answers": ["Tornado", "Tsunami", "Earthquake", "Hurricane"],
        "correct": "Earthquake"
    },
    {
        "question": "Which organ is responsible for pumping blood throughout the human body?",
        "answers": ["Lungs", "Brain", "Heart", "Liver"],
        "correct": "Heart"
    },
    {
        "question": "What is the largest mammal in the world?",
        "answers": ["African Elephant", "Blue Whale", "Giraffe", "Hippopotamus"],
        "correct": "Blue Whale"
    },
    {
        "question": "Which continent is the Sahara Desert located on?",
        "answers": ["Asia", "Africa", "Australia", "South America"],
        "correct": "Africa"
    },
    {
        "question": "Which fruit is known as the king of fruits?",
        "answers": ["Apple", "Mango", "Banana", "Grapes"],
        "correct": "Mango"
    },
    {
        "question": "Who invented the telephone?",
        "answers": ["Thomas Edison", "Alexander Graham Bell", "Nikola Tesla", "James Watt"],
        "correct": "Alexander Graham Bell"
    },
    {
        "question": "Which country is famous for the Great Wall?",
        "answers": ["Japan", "India", "China", "Egypt"],
        "correct": "China"
    },
    {
        "question": "What is the boiling point of water at sea level?",
        "answers": ["50°C", "100°C", "150°C", "200°C"],
        "correct": "100°C"
    },
    {
        "question": "Which river flows through the Grand Canyon?",
        "answers": ["Mississippi River", "Amazon River", "Colorado River", "Nile River"],
        "correct": "Colorado River"
    },
    {
        "question": "What is the primary gas found in the Earth's atmosphere?",
        "answers": ["Oxygen", "Hydrogen", "Carbon Dioxide", "Nitrogen"],
        "correct": "Nitrogen"
    },
    {
        "question": "Who was the Greek god of the sea?",
        "answers": ["Zeus", "Hades", "Poseidon", "Apollo"],
        "correct": "Poseidon"
    },
    {
        "question": "Which famous ship sank in 1912?",
        "answers": ["Bismarck", "Titanic", "Lusitania", "Queen Mary"],
        "correct": "Titanic"
    },
    {
        "question": "What is the currency of the United Kingdom?",
        "answers": ["Euro", "Dollar", "Yen", "Pound Sterling"],
        "correct": "Pound Sterling"
    },
    {
        "question": "Which artist is known for the painting 'Starry Night'?",
        "answers": ["Pablo Picasso", "Vincent van Gogh", "Claude Monet", "Leonardo da Vinci"],
        "correct": "Vincent van Gogh"
    },
    {
        "question": "What is the capital city of France?",
        "answers": ["Paris", "London", "Berlin", "Madrid"],
        "correct": "Paris"
    },
    {
        "question": "Who invented the telephone?",
        "answers": ["Alexander Graham Bell", "Thomas Edison", "Nikola Tesla", "Guglielmo Marconi"],
        "correct": "Nikola Tesla"
    },
    {
        "question": "Which organ in the human body is responsible for detoxification?",
        "answers": ["Heart", "Kidney", "Liver", "Lungs"],
        "correct": "Liver"
    },
    {
        "question": "What is the largest continent by land area?",
        "answers": ["Africa", "North America", "Asia", "Europe"],
        "correct": "Asia"
    },
    {
        "question": "What is the smallest bone in the human body?",
        "answers": ["Ulna", "Femur", "Tibia", "Stapes"],
        "correct": "Stapes"
    },
    {
        "question": "Which country is the leading producer of coffee?",
        "answers": ["Vietnam", "Colombia", "Brazil", "Ethiopia"],
        "correct": "Brazil"
    },
    {
        "question": "How many chambers does the human heart have?",
        "answers": ["2", "4", "6", "8"],
        "correct": "4"
    },
    {
        "question": "What is the tallest building in the world as of 2023?",
        "answers": ["Shanghai Tower", "One World Trade Center", "Burj Khalifa", "Petronas Towers"],
        "correct": "Burj Khalifa"
    },
    {
        "question": "Which country won the FIFA World Cup in 2018?",
        "answers": ["Brazil", "France", "Germany", "Argentina"],
        "correct": "France"
    },
    {
        "question": "What is the longest river in the United States?",
        "answers": ["Mississippi River", "Missouri River", "Colorado River", "Ohio River"],
        "correct": "Missouri River"
    },
    {
        "question": "Which city hosted the 2016 Summer Olympics?",
        "answers": ["London", "Rio de Janeiro", "Tokyo", "Beijing"],
        "correct": "Rio de Janeiro"
    },
    {
        "question": "What is the most abundant gas in Earth's atmosphere?",
        "answers": ["Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"],
        "correct": "Nitrogen"
    },
    {
        "question": "Who wrote the novel '1984'?",
        "answers": ["George Orwell", "Aldous Huxley", "Ray Bradbury", "J.R.R. Tolkien"],
        "correct": "George Orwell"
    },
    {
        "question": "What is the freezing point of water in Celsius?",
        "answers": ["-32°C", "0°C", "32°F", "100°C"],
        "correct": "0°C"
    },
    {
        "question": "Which artist is known for the painting 'The Starry Night'?",
        "answers": ["Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"],
        "correct": "Vincent van Gogh"
    },
    {
        "question": "What is the capital city of Spain?",
        "answers": ["Barcelona", "Valencia", "Madrid", "Seville"],
        "correct": "Madrid"
    },
    {
        "question": "How many teeth does an adult human have?",
        "answers": ["28", "32", "36", "40"],
        "correct": "32"
    },
    {
        "question": "Which country has the most natural lakes?",
        "answers": ["Canada", "United States", "Brazil", "Russia"],
        "correct": "Canada"
    },
    {
        "question": "What is the smallest unit of life?",
        "answers": ["Atom", "Molecule", "Cell", "Organism"],
        "correct": "Cell"
    },
    {
        "question": "Which organ is known as the body's chemical factory?",
        "answers": ["Heart", "Liver", "Brain", "Kidneys"],
        "correct": "Liver"
    },
    {
        "question": "Who painted the ceiling of the Sistine Chapel?",
        "answers": ["Leonardo da Vinci", "Raphael", "Michelangelo", "Donatello"],
        "correct": "Michelangelo"
    },
    {
        "question": "Which city is known as the City of Love?",
        "answers": ["Venice", "Rome", "Paris", "Vienna"],
        "correct": "Paris"
    },
    {
        "question": "What is the highest-grossing film of all time as of 2023?",
        "answers": ["Avatar", "Avengers: Endgame", "Titanic", "Star Wars: The Force Awakens"],
        "correct": "Avatar"
    },
    {
        "question": "What is the capital of South Korea?",
        "answers": ["Busan", "Incheon", "Seoul", "Daegu"],
        "correct": "Seoul"
    },
    {
        "question": "Which ocean is the deepest?",
        "answers": ["Atlantic Ocean", "Pacific Ocean", "Indian Ocean", "Southern Ocean"],
        "correct": "Pacific Ocean"
    },
    {
        "question": "Who discovered America in 1492?",
        "answers": ["Ferdinand Magellan", "Christopher Columbus", "Marco Polo", "Leif Erikson"],
        "correct": "Christopher Columbus"
    },
    {
        "question": "What is the main ingredient in guacamole?",
        "answers": ["Avocado", "Tomato", "Onion", "Jalapeño"],
        "correct": "Avocado"
    },
    {
        "question": "How many planets are in our solar system?",
        "answers": ["7", "8", "9", "10"],
        "correct": "8"
    },
    {
        "question": "What is the most widely eaten fish in the world?",
        "answers": ["Tuna", "Herring", "Salmon", "Cod"],
        "correct": "Herring"
    },
    {
        "question": "What is the name of the first artificial satellite launched into space?",
        "answers": ["Sputnik 1", "Explorer 1", "Luna 1", "Vanguard 1"],
        "correct": "Sputnik 1"
    },
    {
        "question": "Which country is famous for tulips and windmills?",
        "answers": ["Netherlands", "Belgium", "Denmark", "Germany"],
        "correct": "Netherlands"
    },
    {
        "question": "What is the capital of Greece?",
        "answers": ["Thessaloniki", "Patras", "Heraklion", "Athens"],
        "correct": "Athens"
    },
    {
        "question": "What does the acronym 'NASA' stand for?",
        "answers": ["National Aeronautics and Space Agency", "North American Space Association", "National Aeronautics and Space Administration", "National Astronomy and Space Administration"],
        "correct": "National Aeronautics and Space Administration"
    },
    {
        "question": "Who is known as the father of modern physics?",
        "answers": ["Isaac Newton", "Nikola Tesla", "Albert Einstein", "Galileo Galilei"],
        "correct": "Albert Einstein"
    },
    {
        "question": "What is the tallest animal in the world?",
        "answers": ["Elephant", "Giraffe", "Rhino", "Hippo"],
        "correct": "Giraffe"
    },
    {
        "question": "Which is the longest bone in the human body?",
        "answers": ["Humerus", "Tibia", "Femur", "Radius"],
        "correct": "Femur"
    },
    {
        "question": "What is the capital of Egypt?",
        "answers": ["Alexandria", "Giza", "Luxor", "Cairo"],
        "correct": "Cairo"
    },
    {
        "question": "Who was the first female Prime Minister of the United Kingdom?",
        "answers": ["Margaret Thatcher", "Theresa May", "Angela Merkel", "Indira Gandhi"],
        "correct": "Margaret Thatcher"
    },
    {
        "question": "What is the chemical symbol for sodium?",
        "answers": ["S", "Na", "Sn", "So"],
        "correct": "Na"
    },
    {
        "question": "Who is known as the father of computers?",
        "answers": ["Charles Babbage", "Alan Turing", "Bill Gates", "Steve Jobs"],
        "correct": "Charles Babbage"
    },
    {
        "question": "Which animal is known as the King of the Jungle?",
        "answers": ["Tiger", "Lion", "Elephant", "Leopard"],
        "correct": "Lion"
    },
    {
        "question": "What is the currency of the United Kingdom?",
        "answers": ["Euro", "Dollar", "Pound Sterling", "Yen"],
        "correct": "Pound Sterling"
    },
    {
        "question": "What is the largest island in the world?",
        "answers": ["Borneo", "Greenland", "Madagascar", "New Guinea"],
        "correct": "Greenland"
    },
    {
        "question": "Who developed the polio vaccine?",
        "answers": ["Jonas Salk", "Marie Curie", "Alexander Fleming", "Edward Jenner"],
        "correct": "Jonas Salk"
    },
    {
        "question": "Which planet is known as the Red Planet?",
        "answers": ["Venus", "Mars", "Jupiter", "Saturn"],
        "correct": "Mars"
    },
    {
        "question": "What is the hardest natural substance on Earth?",
        "answers": ["Gold", "Iron", "Diamond", "Platinum"],
        "correct": "Diamond"
    },
    {
        "question": "What is the smallest country in the world?",
        "answers": ["Monaco", "Vatican City", "San Marino", "Liechtenstein"],
        "correct": "Vatican City"
    },
    {
        "question": "Which is the largest mammal?",
        "answers": ["African Elephant", "Blue Whale", "Giraffe", "Hippopotamus"],
        "correct": "Blue Whale"
    },
    {
      "question": "Who wrote 'To Kill a Mockingbird'?",
      "answers": ["F. Scott Fitzgerald", "J.D. Salinger", "Harper Lee", "Mark Twain"],
      "correct": "Harper Lee"
    },
    {
      "question": "What is the most abundant element in the universe?",
      "answers": ["Oxygen", "Carbon", "Hydrogen", "Nitrogen"],
      "correct": "Hydrogen"
    },
    {
      "question": "Which river flows through Paris?",
      "answers": ["Rhine", "Thames", "Seine", "Danube"],
      "correct": "Seine"
    },
    {
      "question": "What is the largest country in Africa by land area?",
      "answers": ["Nigeria", "Ethiopia", "Algeria", "Sudan"],
      "correct": "Algeria"
    },
    {
      "question": "Who was the second man to walk on the moon?",
      "answers": ["Neil Armstrong", "Michael Collins", "Buzz Aldrin", "Alan Shepard"],
      "correct": "Buzz Aldrin"
    },
    {
      "question": "What is the primary ingredient in sushi?",
      "answers": ["Rice", "Fish", "Seaweed", "Soy sauce"],
      "correct": "Rice"
    },
    {
      "question": "What is the capital of Germany?",
      "answers": ["Munich", "Frankfurt", "Hamburg", "Berlin"],
      "correct": "Berlin"
    },
    {
      "question": "What is the smallest particle of a chemical element that retains its chemical properties?",
      "answers": ["Atom", "Molecule", "Proton", "Neutron"],
      "correct": "Atom"
    },
    {
      "question": "Who wrote 'Moby Dick'?",
      "answers": ["Charles Dickens", "Herman Melville", "Mark Twain", "Nathaniel Hawthorne"],
      "correct": "Herman Melville"
    },
    {
      "question": "Which planet is known as the Morning Star?",
      "answers": ["Mars", "Venus", "Jupiter", "Saturn"],
      "correct": "Venus"
    },
    {
      "question": "What is the primary language spoken in Mexico?",
      "answers": ["Spanish", "English", "Portuguese", "French"],
      "correct": "Spanish"
    },
    {
      "question": "Which is the largest organ in the human body?",
      "answers": ["Liver", "Heart", "Skin", "Lungs"],
      "correct": "Skin"
    },
    {
      "question": "Who discovered gravity?",
      "answers": ["Isaac Newton", "Albert Einstein", "Galileo Galilei", "Nikola Tesla"],
      "correct": "Isaac Newton"
    },
    {
      "question": "Which country is famous for the Eiffel Tower?",
      "answers": ["France", "Italy", "Germany", "Spain"],
      "correct": "France"
    },
    {
      "question": "What is the main ingredient in traditional Italian pesto?",
      "answers": ["Basil", "Oregano", "Rosemary", "Thyme"],
      "correct": "Basil"
    },
    {
      "question": "Which animal is the symbol of the World Wildlife Fund (WWF)?",
      "answers": ["Elephant", "Panda", "Tiger", "Dolphin"],
      "correct": "Panda"
    },
    {
      "question": "What is the capital of Russia?",
      "answers": ["St. Petersburg", "Moscow", "Vladivostok", "Novosibirsk"],
      "correct": "Moscow"
    },
    {
      "question": "Who painted 'The Last Supper'?",
      "answers": ["Leonardo da Vinci", "Michelangelo", "Raphael", "Titian"],
      "correct": "Leonardo da Vinci"
    },
    {
      "question": "What is the most common blood type in humans?",
      "answers": ["A", "B", "O", "AB"],
      "correct": "O"
    },
    {
      "question": "What is the most populated city in the world as of 2023?",
      "answers": ["New York City", "Mumbai", "Tokyo", "Shanghai"],
      "correct": "Tokyo"
    },
    {
      "question": "What is the capital of India?",
      "answers": ["Mumbai", "New Delhi", "Bangalore", "Kolkata"],
      "correct": "New Delhi"
    },
    {
      "question": "Which gas do plants absorb from the atmosphere?",
      "answers": ["Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"],
      "correct": "Carbon Dioxide"
    },
    {
      "question": "What is the hardest natural material?",
      "answers": ["Gold", "Iron", "Steel", "Diamond"],
      "correct": "Diamond"
    },
    {
      "question": "Who wrote 'The Great Gatsby'?",
      "answers": ["Ernest Hemingway", "John Steinbeck", "F. Scott Fitzgerald", "William Faulkner"],
      "correct": "F. Scott Fitzgerald"
    },
    {
      "question": "What is the capital of Argentina?",
      "answers": ["Cordoba", "Mendoza", "Buenos Aires", "Rosario"],
      "correct": "Buenos Aires"
    },
    {
      "question": "Which is the largest island in the Mediterranean Sea?",
      "answers": ["Sicily", "Cyprus", "Sardinia", "Crete"],
      "correct": "Sicily"
    },
    {
      "question": "What is the process by which plants make their food?",
      "answers": ["Respiration", "Photosynthesis", "Transpiration", "Fermentation"],
      "correct": "Photosynthesis"
    },
    {
      "question": "Who is the author of the Harry Potter series?",
      "answers": ["J.K. Rowling", "J.R.R. Tolkien", "George R.R. Martin", "C.S. Lewis"],
      "correct": "J.K. Rowling"
    },
    {
      "question": "Which planet is known as the Blue Planet?",
      "answers": ["Earth", "Neptune", "Uranus", "Venus"],
      "correct": "Earth"
    },
    {
      "question": "What is the capital city of China?",
      "answers": ["Shanghai", "Guangzhou", "Beijing", "Shenzhen"],
      "correct": "Beijing"
    },
    {
      "question": "Which fruit is known as the king of fruits?",
      "answers": ["Mango", "Apple", "Banana", "Pineapple"],
      "correct": "Mango"
    },
    {
      "question": "What is the chemical symbol for potassium?",
      "answers": ["Po", "K", "P", "Pt"],
      "correct": "K"
    },
    {
      "question": "Who is known as the Bard of Avon?",
      "answers": ["William Shakespeare", "Geoffrey Chaucer", "John Milton", "Robert Burns"],
      "correct": "William Shakespeare"
    },
    {
      "question": "Which country is known as the Land of Maple Leaf?",
      "answers": ["Canada", "Finland", "Norway", "Sweden"],
      "correct": "Canada"
    },
    {
      "question": "Which is the smallest continent by land area?",
      "answers": ["Europe", "South America", "Antarctica", "Australia"],
      "correct": "Australia"
    },
    {
      "question": "What is the main ingredient in sauerkraut?",
      "answers": ["Cucumber", "Cabbage", "Carrot", "Lettuce"],
      "correct": "Cabbage"
    },
    {
      "question": "What is the capital of South Africa?",
      "answers": ["Cape Town", "Johannesburg", "Pretoria", "Durban"],
      "correct": "Pretoria"
    },
    {
      "question": "Which is the oldest university in the world?",
      "answers": ["University of Al Quaraouiyine", "University of Oxford", "University of Bologna", "University of Paris"],
      "correct": "University of Al Quaraouiyine"
    },
    {
      "question": "What is the largest muscle in the human body?",
      "answers": ["Biceps", "Triceps", "Gluteus maximus", "Quadriceps"],
      "correct": "Gluteus maximus"
    },
    {
      "question": "Who discovered the structure of DNA?",
      "answers": ["Gregor Mendel", "Rosalind Franklin", "James Watson and Francis Crick", "Louis Pasteur"],
      "correct": "James Watson and Francis Crick"
    },
    {
      "question": "Which country is known for inventing the hot dog?",
      "answers": ["France", "Italy", "Germany", "United States"],
      "correct": "Germany"
    },
    {
      "question": "What is the capital of Turkey?",
      "answers": ["Istanbul", "Ankara", "Izmir", "Bursa"],
      "correct": "Ankara"
    },
    {
      "question": "Who was the first woman to fly solo across the Atlantic Ocean?",
      "answers": ["Amelia Earhart", "Bessie Coleman", "Harriet Quimby", "Jacqueline Cochran"],
      "correct": "Amelia Earhart"
    },
    {
        "question": "What is the chemical symbol for iron?",
        "answers": ["Ir", "In", "Fe", "Fr"],
        "correct": "Fe"
    },
    {
        "question": "Which animal is known as the ship of the desert?",
        "answers": ["Camel", "Horse", "Elephant", "Donkey"],
        "correct": "Camel"
    },
    {
        "question": "What is the capital of Brazil?",
        "answers": ["Rio de Janeiro", "Sao Paulo", "Brasília", "Salvador"],
        "correct": "Brasília"
    },
    {
        "question": "Which country is known as the Land of Fire and Ice?",
        "answers": ["New Zealand", "Greenland", "Iceland", "Norway"],
        "correct": "Iceland"
    },
    {
        "question": "What is the hardest rock?",
        "answers": ["Marble", "Granite", "Diamond", "Basalt"],
        "correct": "Diamond"
    },
    {
        "question": "Who wrote 'Romeo and Juliet'?",
        "answers": ["Charles Dickens", "Leo Tolstoy", "William Shakespeare", "Mark Twain"],
        "correct": "William Shakespeare"
    },
    {
        "question": "What is the most widely spoken language in the world?",
        "answers": ["Mandarin Chinese", "English", "Spanish", "Hindi"],
        "correct": "Mandarin Chinese"
    },
    {
        "question": "Which is the largest ocean on Earth?",
        "answers": ["Atlantic Ocean", "Pacific Ocean", "Indian Ocean", "Arctic Ocean"],
        "correct": "Pacific Ocean"
    },
    {
        "question": "What is the primary ingredient in tofu?",
        "answers": ["Lentils", "Rice", "Soybeans", "Chickpeas"],
        "correct": "Soybeans"
    },
    {
        "question": "Which planet is known as the Red Planet?",
        "answers": ["Mars", "Venus", "Jupiter", "Saturn"],
        "correct": "Mars"
    },
    {
        "question": "What is the capital of Australia?",
        "answers": ["Sydney", "Melbourne", "Canberra", "Brisbane"],
        "correct": "Canberra"
    },
    {
        "question": "Who developed the theory of relativity?",
        "answers": ["Isaac Newton", "Albert Einstein", "Niels Bohr", "Galileo Galilei"],
        "correct": "Albert Einstein"
    },
    {
        "question": "Which element is known as the building block of life?",
        "answers": ["Carbon", "Hydrogen", "Oxygen", "Nitrogen"],
        "correct": "Carbon"
    },
    {
        "question": "What is the primary language spoken in Brazil?",
        "answers": ["Spanish", "French", "Portuguese", "English"],
        "correct": "Portuguese"
    },
    {
        "question": "Who was the first President of the United States?",
        "answers": ["Thomas Jefferson", "Abraham Lincoln", "George Washington", "John Adams"],
        "correct": "George Washington"
    },
    {
        "question": "What is the largest mammal in the world?",
        "answers": ["Blue whale", "Elephant", "Giraffe", "Hippopotamus"],
        "correct": "Blue whale"
    },
    {
        "question": "What is the main ingredient in a traditional Japanese miso soup?",
        "answers": ["Soybean paste", "Chicken broth", "Fish sauce", "Tofu"],
        "correct": "Soybean paste"
    },
    {
        "question": "What is the capital of Canada?",
        "answers": ["Toronto", "Vancouver", "Ottawa", "Montreal"],
        "correct": "Ottawa"
    },
    {
        "question": "Which gas is essential for human respiration?",
        "answers": ["Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"],
        "correct": "Oxygen"
    },
    {
        "question": "What is the longest river in the world?",
        "answers": ["Nile River", "Amazon River", "Yangtze River", "Mississippi River"],
        "correct": "Nile River"
    },
    {
        "question": "Who is the author of 'Pride and Prejudice'?",
        "answers": ["Jane Austen", "Emily Brontë", "Charlotte Brontë", "Mary Shelley"],
        "correct": "Jane Austen"
    },
    {
        "question": "Which metal is liquid at room temperature?",
        "answers": ["Iron", "Lead", "Mercury", "Zinc"],
        "correct": "Mercury"
    },
    {
        "question": "What is the capital of Italy?",
        "answers": ["Milan", "Venice", "Florence", "Rome"],
        "correct": "Rome"
    },
    {
        "question": "What is the most spoken language in South America?",
        "answers": ["English", "French", "Spanish", "Portuguese"],
        "correct": "Spanish"
    },
    {
        "question": "Who invented the light bulb?",
        "answers": ["Nikola Tesla", "George Westinghouse", "Thomas Edison", "Benjamin Franklin"],
        "correct": "Thomas Edison"
    },
    {
        "question": "What is the smallest planet in our solar system?",
        "answers": ["Mercury", "Venus", "Earth", "Mars"],
        "correct": "Mercury"
    },
    {
        "question": "Who painted the Mona Lisa?",
        "answers": ["Leonardo da Vinci", "Michelangelo", "Raphael", "Rembrandt"],
        "correct": "Leonardo da Vinci"
    },
    {
        "question": "What is the main component of the Earth's atmosphere?",
        "answers": ["Oxygen", "Nitrogen", "Carbon Dioxide", "Argon"],
        "correct": "Nitrogen"
    },
    {
        "question": "Which country has the largest population in the world?",
        "answers": ["India", "China", "United States", "Indonesia"],
        "correct": "China"
    },
    {
        "question": "What is the capital of Japan?",
        "answers": ["Osaka", "Kyoto", "Tokyo", "Hiroshima"],
        "correct": "Tokyo"
    },
    {
        "question": "What is the name of the longest mountain range in the world?",
        "answers": ["Himalayas", "Rockies", "Andes", "Alps"],
        "correct": "Andes"
    },
    {
        "question": "Which scientist is known for the laws of motion?",
        "answers": ["Albert Einstein", "Isaac Newton", "Galileo Galilei", "Johannes Kepler"],
        "correct": "Isaac Newton"
    },
    {
        "question": "What is the main ingredient in paella?",
        "answers": ["Pasta", "Bread", "Rice", "Potatoes"],
        "correct": "Rice"
    },
    {
        "question": "What is the largest desert in the world?",
        "answers": ["Arabian Desert", "Gobi Desert", "Sahara Desert", "Kalahari Desert"],
        "correct": "Sahara Desert"
    },
    {
        "question": "Who is the author of 'The Odyssey'?",
        "answers": ["Plato", "Socrates", "Homer", "Virgil"],
        "correct": "Homer"
    },
    {
        "question": "What is the capital of the United States?",
        "answers": ["New York", "Los Angeles", "Washington, D.C.", "Chicago"],
        "correct": "Washington, D.C."
    },
    {
        "question": "What is the chemical symbol for gold?",
        "answers": ["Ag", "Au", "Fe", "Pt"],
        "correct": "Au"
    },
    {
        "question": "Which country is known for the Great Wall?",
        "answers": ["China", "Japan", "India", "South Korea"],
        "correct": "China"
    },
    {
        "question": "What is the largest organ in the human body?",
        "answers": ["Liver", "Heart", "Skin", "Lungs"],
        "correct": "Skin"
    },
    {
        "question": "Which ocean is the smallest?",
        "answers": ["Atlantic Ocean", "Indian Ocean", "Southern Ocean", "Arctic Ocean"],
        "correct": "Arctic Ocean"
    },
    {
        "question": "Who developed the theory of evolution by natural selection?",
        "answers": ["Gregor Mendel", "Charles Darwin", "Jean-Baptiste Lamarck", "Alfred Russel Wallace"],
        "correct": "Charles Darwin"
    },
    {
        "question": "What is the capital of Saudi Arabia?",
        "answers": ["Jeddah", "Mecca", "Medina", "Riyadh"],
        "correct": "Riyadh"
    },
    {
        "question": "Which gas is used in the process of photosynthesis?",
        "answers": ["Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"],
        "correct": "Carbon Dioxide"
    },
    {
        "question": "Who is known as the Maid of Orleans?",
        "answers": ["Joan of Arc", "Catherine the Great", "Marie Antoinette", "Eleanor of Aquitaine"],
        "correct": "Joan of Arc"
    },
    {
        "question": "What is the capital of Portugal?",
        "answers": ["Porto", "Faro", "Lisbon", "Coimbra"],
        "correct": "Lisbon"
    },
    {
        "question": "What is the currency of Japan?",
        "answers": ["Won", "Yuan", "Dollar", "Yen"],
        "correct": "Yen"
    },
    {
        "question": "Who is the author of 'The Catcher in the Rye'?",
        "answers": ["Ernest Hemingway", "J.D. Salinger", "John Steinbeck", "William Faulkner"],
        "correct": "J.D. Salinger"
    },
    {
        "question": "What is the smallest country in the world?",
        "answers": ["Monaco", "San Marino", "Vatican City", "Liechtenstein"],
        "correct": "Vatican City"
    },
    {
        "question": "Which planet is closest to the Sun?",
        "answers": ["Venus", "Earth", "Mercury", "Mars"],
        "correct": "Mercury"
    },
    {
        "question": "What is the capital of Kenya?",
        "answers": ["Mombasa", "Kisumu", "Nairobi", "Eldoret"],
        "correct": "Nairobi"
    },
    {
        "question": "Which element has the chemical symbol 'O'?",
        "answers": ["Oxygen", "Osmium", "Gold", "Potassium"],
        "correct": "Oxygen"
    },
    {
        "question": "Who is the author of 'The Hobbit'?",
        "answers": ["J.R.R. Tolkien", "C.S. Lewis", "George Orwell", "J.K. Rowling"],
        "correct": "J.R.R. Tolkien"
    },
    {
        "question": "Which ocean is Bermuda located in?",
        "answers": ["Indian Ocean", "Pacific Ocean", "Atlantic Ocean", "Southern Ocean"],
        "correct": "Atlantic Ocean"
    },
    {
        "question": "What is the capital of Norway?",
        "answers": ["Bergen", "Trondheim", "Oslo", "Stavanger"],
        "correct": "Oslo"
    },
    {
        "question": "Who wrote 'Jane Eyre'?",
        "answers": ["Jane Austen", "Emily Brontë", "Charlotte Brontë", "Mary Shelley"],
        "correct": "Charlotte Brontë"
    },
    {
        "question": "What is the capital of Spain?",
        "answers": ["Barcelona", "Valencia", "Madrid", "Seville"],
        "correct": "Madrid"
    },
    {
        "question": "Who painted the ceiling of the Sistine Chapel?",
        "answers": ["Leonardo da Vinci", "Michelangelo", "Raphael", "Donatello"],
        "correct": "Michelangelo"
    },
    {
        "question": "Which is the longest bone in the human body?",
        "answers": ["Humerus", "Femur", "Tibia", "Fibula"],
        "correct": "Femur"
    },
    {
        "question": "What is the chemical symbol for sodium?",
        "answers": ["S", "Na", "N", "So"],
        "correct": "Na"
    },
    {
        "question": "Who wrote 'The Divine Comedy'?",
        "answers": ["Dante Alighieri", "Geoffrey Chaucer", "John Milton", "Virgil"],
        "correct": "Dante Alighieri"
    },
    {
        "question": "What is the main ingredient in gazpacho?",
        "answers": ["Meat", "Tomatoes", "Potatoes", "Cheese"],
        "correct": "Tomatoes"
    },
    {
        "question": "What is the capital of Greece?",
        "answers": ["Thessaloniki", "Patras", "Athens", "Heraklion"],
        "correct": "Athens"
    },
    {
        "question": "Who discovered penicillin?",
        "answers": ["Louis Pasteur", "Alexander Fleming", "Robert Koch", "Joseph Lister"],
        "correct": "Alexander Fleming"
    },
    {
        "question": "What is the smallest country in Europe by land area?",
        "answers": ["Monaco", "Liechtenstein", "Vatican City", "San Marino"],
        "correct": "Vatican City"
    },
    {
        "question": "Which planet is known as the Earth's twin?",
        "answers": ["Mars", "Venus", "Mercury", "Neptune"],
        "correct": "Venus"
    },
    {
        "question": "Who is the main character in the novel '1984'?",
        "answers": ["Big Brother", "Julia", "Winston Smith", "O'Brien"],
        "correct": "Winston Smith"
    },
    {
        "question": "What is the capital of Thailand?",
        "answers": ["Phuket", "Bangkok", "Chiang Mai", "Pattaya"],
        "correct": "Bangkok"
    },
    {
        "question": "Which element has the chemical symbol 'He'?",
        "answers": ["Hydrogen", "Helium", "Hafnium", "Holmium"],
        "correct": "Helium"
    },
    {
        "question": "Who wrote 'The Old Man and the Sea'?",
        "answers": ["Ernest Hemingway", "William Faulkner", "F. Scott Fitzgerald", "John Steinbeck"],
        "correct": "Ernest Hemingway"
    },
    {
        "question": "What is the capital of Poland?",
        "answers": ["Krakow", "Warsaw", "Gdansk", "Wroclaw"],
        "correct": "Warsaw"
    },
    {
        "question": "Which is the highest mountain in Africa?",
        "answers": ["Mount Kenya", "Mount Kilimanjaro", "Mount Elgon", "Mount Meru"],
        "correct": "Mount Kilimanjaro"
    },
    {
        "question": "What is the main ingredient in French ratatouille?",
        "answers": ["Chicken", "Fish", "Vegetables", "Beef"],
        "correct": "Vegetables"
    },
    {
        "question": "Who is known as the father of modern physics?",
        "answers": ["Albert Einstein", "Isaac Newton", "Galileo Galilei", "Niels Bohr"],
        "correct": "Albert Einstein"
    },
    {
        "question": "What is the capital of Egypt?",
        "answers": ["Alexandria", "Luxor", "Cairo", "Aswan"],
        "correct": "Cairo"
    },
    {
        "question": "Which planet is known as the evening star?",
        "answers": ["Mars", "Venus", "Jupiter", "Saturn"],
        "correct": "Venus"
    },
    {
        "question": "Who is the author of 'Brave New World'?",
        "answers": ["George Orwell", "Aldous Huxley", "Ray Bradbury", "H.G. Wells"],
        "correct": "Aldous Huxley"
    },
    {
        "question": "What is the chemical symbol for lead?",
        "answers": ["Ld", "Pl", "Pb", "Ln"],
        "correct": "Pb"
    },
    {
        "question": "What is the capital of Iraq?",
        "answers": ["Basra", "Mosul", "Baghdad", "Erbil"],
        "correct": "Baghdad"
    },
    {
        "question": "Which country is known as the Land of the Rising Sun?",
        "answers": ["Japan", "China", "South Korea", "Thailand"],
        "correct": "Japan"
    },
    {
        "question": "What is the largest continent by land area?",
        "answers": ["Africa", "North America", "Europe", "Asia"],
        "correct": "Asia"
    },
    {
        "question": "Who painted 'Starry Night'?",
        "answers": ["Pablo Picasso", "Vincent van Gogh", "Claude Monet", "Leonardo da Vinci"],
        "correct": "Vincent van Gogh"
    },
    {
        "question": "What is the primary language spoken in Italy?",
        "answers": ["Spanish", "French", "Italian", "German"],
        "correct": "Italian"
    },
    {
        "question": "Who was the first woman to win a Nobel Prize?",
        "answers": ["Marie Curie", "Mother Teresa", "Jane Addams", "Rosalind Franklin"],
        "correct": "Marie Curie"
    },
    {
        "question": "What is the capital of Iran?",
        "answers": ["Isfahan", "Shiraz", "Mashhad", "Tehran"],
        "correct": "Tehran"
    },
    {
        "question": "Which planet is known as the gas giant?",
        "answers": ["Mars", "Jupiter", "Venus", "Mercury"],
        "correct": "Jupiter"
    },
    {
        "question": "Who wrote 'Les Misérables'?",
        "answers": ["Alexandre Dumas", "Victor Hugo", "Gustave Flaubert", "Émile Zola"],
        "correct": "Victor Hugo"
    },
    {
        "question": "What is the main ingredient in the traditional British dish 'fish and chips'?",
        "answers": ["Salmon and bread", "Cod and potatoes", "Haddock and peas", "Tuna and rice"],
        "correct": "Cod and potatoes"
    },
    {
        "question": "What is the chemical symbol for silver?",
        "answers": ["Si", "Ag", "Au", "Al"],
        "correct": "Ag"
    },
    {
        "question": "Which country is home to the kangaroo?",
        "answers": ["Australia", "New Zealand", "South Africa", "Brazil"],
        "correct": "Australia"
    },
    {
        "question": "Who wrote 'War and Peace'?",
        "answers": ["Fyodor Dostoevsky", "Anton Chekhov", "Leo Tolstoy", "Ivan Turgenev"],
        "correct": "Leo Tolstoy"
    },
    {
        "question": "What is the capital of Sweden?",
        "answers": ["Gothenburg", "Malmö", "Uppsala", "Stockholm"],
        "correct": "Stockholm"
    },
    {
        "question": "Which element has the chemical symbol 'N'?",
        "answers": ["Sodium", "Nitrogen", "Nickel", "Neodymium"],
        "correct": "Nitrogen"
    },
    {
        "question": "What is the capital of South Korea?",
        "answers": ["Busan", "Incheon", "Seoul", "Daegu"],
        "correct": "Seoul"
    },
    {
        "question": "Who is the author of 'The Alchemist'?",
        "answers": ["Gabriel Garcia Marquez", "Paulo Coelho", "Isabel Allende", "Mario Vargas Llosa"],
        "correct": "Paulo Coelho"
    },
    {
        "question": "Which planet is known as the ice giant?",
        "answers": ["Jupiter", "Neptune", "Mars", "Mercury"],
        "correct": "Neptune"
    },
    {
        "question": "What is the primary ingredient in the Indian dish 'biryani'?",
        "answers": ["Rice", "Bread", "Lentils", "Potatoes"],
        "correct": "Rice"
    },
    {
        "question": "Who wrote 'Don Quixote'?",
        "answers": ["Miguel de Cervantes", "Federico Garcia Lorca", "Gabriel Garcia Marquez", "Pablo Neruda"],
        "correct": "Miguel de Cervantes"
    },
    {
        "question": "What is the capital of Colombia?",
        "answers": ["Medellin", "Cali", "Bogotá", "Cartagena"],
        "correct": "Bogotá"
    },
    {
        "question": "Which element has the chemical symbol 'C'?",
        "answers": ["Carbon", "Calcium", "Cobalt", "Chlorine"],
        "correct": "Carbon"
    },
    {
        "question": "Who is known as the father of psychoanalysis?",
        "answers": ["Sigmund Freud", "Carl Jung", "Alfred Adler", "Wilhelm Reich"],
        "correct": "Sigmund Freud"
    },
    {
        "question": "What is the main ingredient in guacamole?",
        "answers": ["Tomato", "Onion", "Avocado", "Pepper"],
        "correct": "Avocado"
    },
    {
        "question": "What is the capital of Vietnam?",
        "answers": ["Ho Chi Minh City", "Da Nang", "Hanoi", "Hue"],
        "correct": "Hanoi"
    },
    {
        "question": "Which country is known for the Taj Mahal?",
        "answers": ["India", "Pakistan", "Bangladesh", "Nepal"],
        "correct": "India"
    },
    {
        "question": "What is the capital of the Philippines?",
        "answers": ["Cebu", "Davao", "Quezon City", "Manila"],
        "correct": "Manila"
    },
    {
        "question": "Who is the author of 'Frankenstein'?",
        "answers": ["Mary Shelley", "Bram Stoker", "H.G. Wells", "Jules Verne"],
        "correct": "Mary Shelley"
    },
    {
        "question": "What is the main ingredient in the Japanese dish sushi?",
        "answers": ["Bread", "Noodles", "Rice", "Potatoes"],
        "correct": "Rice"
    },
    {
        "question": "Who painted 'The Persistence of Memory'?",
        "answers": ["Pablo Picasso", "Salvador Dalí", "Claude Monet", "Vincent van Gogh"],
        "correct": "Salvador Dalí"
    },
    {
        "question": "What is the capital of Argentina?",
        "answers": ["Rosario", "Mendoza", "Buenos Aires", "Cordoba"],
        "correct": "Buenos Aires"
    },
    {
        "question": "Which planet is known as the Earth's sister planet?",
        "answers": ["Mars", "Venus", "Mercury", "Jupiter"],
        "correct": "Venus"
    },
    {
        "question": "Who wrote 'The Great Gatsby'?",
        "answers": ["F. Scott Fitzgerald", "Ernest Hemingway", "John Steinbeck", "William Faulkner"],
        "correct": "F. Scott Fitzgerald"
    },
    {
        "question": "What is the primary ingredient in hummus?",
        "answers": ["Lentils", "Peas", "Chickpeas", "Beans"],
        "correct": "Chickpeas"
    },
    {
        "question": "Who is known as the father of modern chemistry?",
        "answers": ["Isaac Newton", "Albert Einstein", "Antoine Lavoisier", "Dmitri Mendeleev"],
        "correct": "Antoine Lavoisier"
    },
    {
        "question": "What is the capital of Chile?",
        "answers": ["Valparaiso", "Concepcion", "Santiago", "La Serena"],
        "correct": "Santiago"
    },
    {
        "question": "Which element has the chemical symbol 'K'?",
        "answers": ["Calcium", "Potassium", "Krypton", "Chromium"],
        "correct": "Potassium"
    },
    {
        "question": "Who wrote 'To Kill a Mockingbird'?",
        "answers": ["Harper Lee", "Mark Twain", "J.D. Salinger", "F. Scott Fitzgerald"],
        "correct": "Harper Lee"
    },
    {
        "question": "What is the main ingredient in tzatziki?",
        "answers": ["Tomato", "Yogurt", "Cheese", "Olive oil"],
        "correct": "Yogurt"
    },
    {
        "question": "Who painted 'The Birth of Venus'?",
        "answers": ["Leonardo da Vinci", "Michelangelo", "Sandro Botticelli", "Raphael"],
        "correct": "Sandro Botticelli"
    },
    {
        "question": "What is the capital of Peru?",
        "answers": ["Cusco", "Arequipa", "Lima", "Trujillo"],
        "correct": "Lima"
    },
    {
        "question": "Which element has the chemical symbol 'Fe'?",
        "answers": ["Iron", "Francium", "Fluorine", "Fermium"],
        "correct": "Iron"
    },
    {
        "question": "Who wrote 'Moby-Dick'?",
        "answers": ["Nathaniel Hawthorne", "Herman Melville", "Edgar Allan Poe", "Mark Twain"],
        "correct": "Herman Melville"
    },
    {
        "question": "What is the main ingredient in pesto?",
        "answers": ["Basil", "Mint", "Parsley", "Oregano"],
        "correct": "Basil"
    },
    {
        "question": "What is the capital of Belgium?",
        "answers": ["Antwerp", "Ghent", "Brussels", "Bruges"],
        "correct": "Brussels"
    },
    {
        "question": "Which gas is used in the process of photosynthesis?",
        "answers": ["Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"],
        "correct": "Carbon Dioxide"
    },
    {
        "question": "Which element has the chemical symbol 'H'?",
        "answers": ["Hydrogen", "Helium", "Hafnium", "Holmium"],
        "correct": "Hydrogen"
    },
    {
        "question": "Who wrote 'Wuthering Heights'?",
        "answers": ["Jane Austen", "Emily Brontë", "Charlotte Brontë", "Mary Shelley"],
        "correct": "Emily Brontë"
    },
    {
        "question": "What is the capital of Switzerland?",
        "answers": ["Zurich", "Geneva", "Bern", "Basel"],
        "correct": "Bern"
    },
    {
        "question": "Which planet is known for its Great Dark Spot?",
        "answers": ["Jupiter", "Neptune", "Saturn", "Uranus"],
        "correct": "Neptune"
    },
    {
        "question": "What is the main ingredient in sauerkraut?",
        "answers": ["Cabbage", "Carrots", "Potatoes", "Cucumbers"],
        "correct": "Cabbage"
    },
    {
        "question": "Who painted 'The Scream'?",
        "answers": ["Edvard Munch", "Vincent van Gogh", "Pablo Picasso", "Claude Monet"],
        "correct": "Edvard Munch"
    },
    {
        "question": "What is the capital of Denmark?",
        "answers": ["Aarhus", "Odense", "Copenhagen", "Aalborg"],
        "correct": "Copenhagen"
    },
    {
        "question": "Which element has the chemical symbol 'Ca'?",
        "answers": ["Calcium", "Carbon", "Cadmium", "Caesium"],
        "correct": "Calcium"
    },
    {
        "question": "Who wrote 'Crime and Punishment'?",
        "answers": ["Leo Tolstoy", "Fyodor Dostoevsky", "Anton Chekhov", "Ivan Turgenev"],
        "correct": "Fyodor Dostoevsky"
    },
    {
        "question": "What is the capital of Finland?",
        "answers": ["Espoo", "Tampere", "Helsinki", "Turku"],
        "correct": "Helsinki"
    },
    {
        "question": "Which planet is known for its blue color?",
        "answers": ["Jupiter", "Mars", "Neptune", "Mercury"],
        "correct": "Neptune"
    },
    {
        "question": "What is the main ingredient in the Indian dish 'paneer tikka'?",
        "answers": ["Chicken", "Lamb", "Cheese", "Fish"],
        "correct": "Cheese"
    },
    {
        "question": "Who wrote 'The Iliad'?",
        "answers": ["Plato", "Socrates", "Homer", "Aristophanes"],
        "correct": "Homer"
    },
    {
        "question": "What is the capital of Austria?",
        "answers": ["Salzburg", "Innsbruck", "Vienna", "Graz"],
        "correct": "Vienna"
    },
    {
        "question": "Which element has the chemical symbol 'S'?",
        "answers": ["Silicon", "Sulfur", "Scandium", "Selenium"],
        "correct": "Sulfur"
    },
    {
        "question": "Who wrote 'The Picture of Dorian Gray'?",
        "answers": ["Charles Dickens", "Oscar Wilde", "George Eliot", "Thomas Hardy"],
        "correct": "Oscar Wilde"
    },
    {
        "question": "What is the capital of Hungary?",
        "answers": ["Debrecen", "Szeged", "Budapest", "Pécs"],
        "correct": "Budapest"
    },
    {
        "question": "Which planet is known for its many moons?",
        "answers": ["Mars", "Venus", "Jupiter", "Mercury"],
        "correct": "Jupiter"
    },
    {
        "question": "What is the main ingredient in the Middle Eastern dish falafel?",
        "answers": ["Chickpeas", "Lentils", "Beans", "Peas"],
        "correct": "Chickpeas"
    },
    {
        "question": "Who painted 'Guernica'?",
        "answers": ["Pablo Picasso", "Salvador Dalí", "Diego Rivera", "Joan Miró"],
        "correct": "Pablo Picasso"
    },
    {
        "question": "What is the capital of the Netherlands?",
        "answers": ["Rotterdam", "The Hague", "Amsterdam", "Utrecht"],
        "correct": "Amsterdam"
    },
    {
        "question": "Which element has the chemical symbol 'Ni'?",
        "answers": ["Nickel", "Nitrogen", "Neodymium", "Nihonium"],
        "correct": "Nickel"
    },
    {
        "question": "Who wrote 'Pride and Prejudice'?",
        "answers": ["Jane Austen", "Emily Brontë", "Charlotte Brontë", "Mary Shelley"],
        "correct": "Jane Austen"
    },
    {
        "question": "What is the main ingredient in the Mexican dish 'tacos'?",
        "answers": ["Rice", "Tortillas", "Beans", "Cheese"],
        "correct": "Tortillas"
    },
    {
        "question": "Who painted 'The Last Supper'?",
        "answers": ["Leonardo da Vinci", "Michelangelo", "Raphael", "Donatello"],
        "correct": "Leonardo da Vinci"
    },
    {
        "question": "What is the capital of Portugal?",
        "answers": ["Porto", "Faro", "Lisbon", "Coimbra"],
        "correct": "Lisbon"
    },
    {
        "question": "Which element has the chemical symbol 'Mg'?",
        "answers": ["Manganese", "Magnesium", "Molybdenum", "Mercury"],
        "correct": "Magnesium"
    },
    {
        "question": "Who wrote 'Anna Karenina'?",
        "answers": ["Leo Tolstoy", "Fyodor Dostoevsky", "Anton Chekhov", "Ivan Turgenev"],
        "correct": "Leo Tolstoy"
    },
    {
        "question": "What is the capital of the Czech Republic?",
        "answers": ["Brno", "Ostrava", "Prague", "Plzen"],
        "correct": "Prague"
    },
    {
        "question": "Which planet is known for its Great Red Spot?",
        "answers": ["Jupiter", "Mars", "Saturn", "Neptune"],
        "correct": "Jupiter"
    },
    {
        "question": "What is the main ingredient in the Spanish dish 'paella'?",
        "answers": ["Bread", "Potatoes", "Rice", "Pasta"],
        "correct": "Rice"
    }
]

# Verbindung zur PostgreSQL-Datenbank herstellen
conn = psycopg2.connect(
    dbname="infprojectdb",
    user="admin",
    password="X4a9Bg3!",
    host="iab-access.ddns.net",
    port="5431"
)

cur = conn.cursor()


# Daten in die Tabelle einfügen
for q in questions_data:
    cur.execute('''
    INSERT INTO question (data, answer, points, a, b, c, d)
    VALUES (%s, %s, %s, %s, %s, %s, %s);
    ''', (q['question'],q['correct'],1, q['answers'][0], q['answers'][1], q['answers'][2], q['answers'][3]))

# Änderungen speichern und Verbindung schließen
conn.commit()
cur.close()
conn.close()

print("Daten erfolgreich in die Datenbank eingefügt!")
