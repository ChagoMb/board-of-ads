package com.avito.configs;

import com.avito.models.Category;
import com.avito.models.Role;
import com.avito.models.User;
import com.avito.models.posting.Posting;
import com.avito.service.interfaces.CategoryService;
import com.avito.service.interfaces.PostingService;
import com.avito.service.interfaces.RoleService;
import com.avito.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final RoleService roleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final PostingService postingService;

    @PostConstruct
    private void init() {
        initRoles();
        initUsers();
        initCategories();
        initPostings();
    }

    private void initRoles() {
        if (roleService.getAllRoles().size() != 0) {
            return;
        }

        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        roleService.addRole(adminRole);
        roleService.addRole(userRole);
    }

    private void initUsers() {
        if (userService.getAllUsers().size() != 0) {
            return;
        }

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleService.findRoleByName("ADMIN"));
        adminRoles.add(roleService.findRoleByName("USER"));

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findRoleByName("USER"));

        User userAdmin = new User("admin@gmail.com", "Test Admin name", "admin", "admin", "89185552623", adminRoles);
        User userUser = new User("test.email.1@gmail.com", "test 1 public name", "qwerty1", "qwerty1", "89526321452", userRoles);

        userService.addUser(userAdmin);
        userService.addUser(userUser);
    }

    private void initPostings() {
        if (postingService.getAllPostings().size() != 0) {
            return;
        }

        User userUser = userService.findUserByEmail("test.email.1@gmail.com");
        User adminUser = userService.findUserByEmail("admin@gmail.com");

        List<Category> categories = categoryService.getRootCategories().stream().sorted((Comparator.comparing(Category::getName))).collect(Collectors.toList());
        assert categories.size() >= 3;

        Posting posting = new Posting();
        posting.setTitle("Коттедж 400 м² на участке 3 сот.");
        posting.setCategory(categories.get(0));
        posting.setUser(adminUser);
        posting.setFullDescription("Коттедж на два хозяина. На первом этаже кухня, зал и туалет с душем, второй этаж три комнаты и туалет с ванной, третий этаж-две комнаты. Цокольный этаж с гаражом и комнатой. Готов к заселению, возможна долгосрочная аренда.");
        posting.setShortDescription("Коттедж");
        posting.setPrice(3_500_000);
        postingService.addPosting(posting);

        posting = new Posting();
        posting.setTitle("Posting title");
        posting.setCategory(categories.get(1));
        posting.setUser(adminUser);
        posting.setFullDescription("Full description, long text.");
        posting.setShortDescription("Some posting");
        posting.setPrice(30_000);
        postingService.addPosting(posting);

        posting = new Posting();
        posting.setTitle("Posting title 2");
        posting.setCategory(categories.get(2));
        posting.setUser(userUser);
        posting.setFullDescription("Full description, long text. (owner must be user)");
        posting.setShortDescription("Some posting of user");
        posting.setPrice(4_500);
        postingService.addPosting(posting);
    }

    private void initCategories() {
        if (categoryService.getAllCategories().size() > 8) {
            return;
        }

        Set<Category> emptySet = Collections.EMPTY_SET;

        HashSet<Category> categoryJob2level = new HashSet<>();
        HashSet<Category> categoryVacancies3level = new HashSet<>();
        HashSet<Category> categorySummary3level = new HashSet<>();

        HashSet<Category> categoryTransport2level = new HashSet<>();
        HashSet<Category> categoryCars3level = new HashSet<>();
        HashSet<Category> categoryWaterTransport3level = new HashSet<>();
        HashSet<Category> categoryTrucksAndSpecialEquipment3level = new HashSet<>();
        HashSet<Category> categoryMotorcycles3level = new HashSet<>();

        HashSet<Category> categoryProperty2level = new HashSet<>();
        HashSet<Category> categoryApartments3level = new HashSet<>();
        HashSet<Category> categoryRooms3level = new HashSet<>();
        HashSet<Category> categoryHousesSummerResidencesCottages3level = new HashSet<>();
        HashSet<Category> categoryLand3level = new HashSet<>();
        HashSet<Category> categoryGaragesAndParkingSpaces3level = new HashSet<>();
        HashSet<Category> categoryCommercialProperty3level = new HashSet<>();
        HashSet<Category> categoryPropertyAbroad3level = new HashSet<>();

        //1 вкладка Категории

        Category categoryTransport = new Category("Транспорт", null, categoryTransport2level);
        Category categoryProperty = new Category("Недвижимость", null, categoryProperty2level);
        Category categoryJob = new Category("Работа", null, categoryJob2level);
        Category categoryServices = new Category("Услуги", null, emptySet);
        Category categoryForHomeAndGarden = new Category("Для дома и дачи", null, emptySet);
        Category categoryPersonalItems = new Category("Личные вещи", null, emptySet);
        Category categoryConsumerElectronics = new Category("Бытовая электроника", null, emptySet);
        Category categoryHobbiesAndLeisure = new Category("Хобби и отдых", null, emptySet);
        Category cateroryAnimals = new Category("Животные", null, emptySet);
        Category categoryBusiness = new Category("Для бизнеса", null, emptySet);

        //2 вкладка Категории --> транспорт

        Category categoryCars = new Category("Автомобили", categoryTransport, categoryCars3level);
        categoryTransport2level.add(categoryCars);
        Category categoryMotorcycles = new Category("Мотоциклы и мототехника", categoryTransport, categoryMotorcycles3level);
        categoryTransport2level.add(categoryMotorcycles);
        Category categoryTrucksAndSpecialEquipment = new Category("Грузовики и спецтехника", categoryTransport, categoryTrucksAndSpecialEquipment3level);
        categoryTransport2level.add(categoryTrucksAndSpecialEquipment);
        Category categoryWaterTransport = new Category("Водный транспорт", categoryTransport, categoryWaterTransport3level);
        categoryTransport2level.add(categoryWaterTransport);
        Category categoryPartsAndAccessories = new Category("Запчасти и аксессуары", categoryTransport, emptySet);
        categoryTransport2level.add(categoryPartsAndAccessories);

        //3 вкладка Категории --> транспорт --> автомобили

        categoryCars3level.add(new Category("С пробегом", categoryCars, emptySet));
        categoryCars3level.add(new Category("Новый", categoryCars, emptySet));

        //3 вкладка Категории --> транспорт --> водный транспорт

        categoryWaterTransport3level.add(new Category("Вёсельные лодки", categoryWaterTransport, emptySet));
        categoryWaterTransport3level.add(new Category("Гидроциклы", categoryWaterTransport, emptySet));
        categoryWaterTransport3level.add(new Category("Катера и яхты", categoryWaterTransport, emptySet));
        categoryWaterTransport3level.add(new Category("Каяки и каноэ", categoryWaterTransport, emptySet));
        categoryWaterTransport3level.add(new Category("Моторные лодки", categoryWaterTransport, emptySet));
        categoryWaterTransport3level.add(new Category("Надувные лодки", categoryWaterTransport, emptySet));

        //3 вкладка Категории --> транспорт -->  спец транспорт

        categoryTrucksAndSpecialEquipment3level.add(new Category("Автобусы", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Автодома", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Автокраны", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Бульдозеры", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Грузовики", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Коммунальная техника", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Лёгкий транспорт", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Погрузчики", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Прицепы", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Сельхозтехника", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Строительная техника", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Техника для лесозаготовки", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Тягачи", categoryTrucksAndSpecialEquipment, emptySet));
        categoryTrucksAndSpecialEquipment3level.add(new Category("Экскаваторы", categoryTrucksAndSpecialEquipment, emptySet));

        //3 вкладка Категории --> транспорт -->  мотоциклы

        categoryMotorcycles3level.add(new Category("Багги", categoryMotorcycles, emptySet));
        categoryMotorcycles3level.add(new Category("Вездеходы", categoryMotorcycles, emptySet));
        categoryMotorcycles3level.add(new Category("Картинг", categoryMotorcycles, emptySet));
        categoryMotorcycles3level.add(new Category("Квадроциклы", categoryMotorcycles, emptySet));
        categoryMotorcycles3level.add(new Category("Мопеды и скутеры", categoryMotorcycles, emptySet));
        categoryMotorcycles3level.add(new Category("Мотоциклы", categoryMotorcycles, emptySet));
        categoryMotorcycles3level.add(new Category("Снегоходы", categoryMotorcycles, emptySet));

        // 2 вкладка   Категории  --> Работа

        Category categorySummary = new Category("Резюме", categoryJob, categorySummary3level);
        categoryJob2level.add(categorySummary);
        Category categoryVacancies = new Category("Вакансии", categoryJob, categoryVacancies3level);
        categoryJob2level.add(categoryVacancies);

        //3 вкладка   Категории --> Работа  --> Резюме и Вакансии

        categoryVacancies3level.add(new Category("IT, интернет, телеком", categorySummary, emptySet));
        categorySummary3level.add(new Category("IT, интернет, телеком", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Автомобильный бизнес", categorySummary, emptySet));
        categorySummary3level.add(new Category("Автомобильный бизнес", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Административная работа", categorySummary, emptySet));
        categorySummary3level.add(new Category("Административная работа", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Банки, инвестиции", categorySummary, emptySet));
        categorySummary3level.add(new Category("Банки, инвестиции", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Без опыта, студенты", categorySummary, emptySet));
        categorySummary3level.add(new Category("Без опыта, студенты", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Бухгалтерия, финансы", categorySummary, emptySet));
        categorySummary3level.add(new Category("Бухгалтерия, финансы", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Высший менеджмент", categorySummary, emptySet));
        categorySummary3level.add(new Category("Высший менеджмент", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Госслужба, НКО", categorySummary, emptySet));
        categorySummary3level.add(new Category("Госслужба, НКО", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Домашний персонал", categorySummary, emptySet));
        categorySummary3level.add(new Category("Домашний персонал", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("ЖКХ, эксплуатация", categorySummary, emptySet));
        categorySummary3level.add(new Category("ЖКХ, эксплуатация", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Искусство, развлечения", categorySummary, emptySet));
        categorySummary3level.add(new Category("Искусство, развлечения", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Консультирование", categorySummary, emptySet));
        categorySummary3level.add(new Category("Консультирование", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Маркетинг, реклама, PR", categorySummary, emptySet));
        categorySummary3level.add(new Category("Маркетинг, реклама, PR", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Медицина, фармацевтика", categorySummary, emptySet));
        categorySummary3level.add(new Category("Медицина, фармацевтика", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Образование, наука", categorySummary, emptySet));
        categorySummary3level.add(new Category("Образование, наука", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Охрана, безопасность", categorySummary, emptySet));
        categorySummary3level.add(new Category("Охрана, безопасность", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Продажи", categorySummary, emptySet));
        categorySummary3level.add(new Category("Продажи", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Производство, сырьё, с/х", categorySummary, emptySet));
        categorySummary3level.add(new Category("Производство, сырьё, с/х", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Страхование", categorySummary, emptySet));
        categorySummary3level.add(new Category("Страхование", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Строительство", categorySummary, emptySet));
        categorySummary3level.add(new Category("Строительство", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Транспорт, логистика", categorySummary, emptySet));
        categorySummary3level.add(new Category("Транспорт, логистика", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Туризм, рестораны", categorySummary, emptySet));
        categorySummary3level.add(new Category("Туризм, рестораны", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Фитнес, салоны красоты", categorySummary, emptySet));
        categorySummary3level.add(new Category("Фитнес, салоны красоты", categoryVacancies, emptySet));
        categoryVacancies3level.add(new Category("Юриспруденция", categorySummary, emptySet));
        categorySummary3level.add(new Category("Юриспруденция", categoryVacancies, emptySet));

        //2 вкладка   Категории -->  Недвижимость

        Category categoryApartments = new Category("Квартиры", categoryProperty, categoryApartments3level);
        categoryProperty2level.add(categoryApartments);
        Category categoryRooms = new Category("Комнаты", categoryProperty, categoryRooms3level);
        categoryProperty2level.add(categoryRooms);
        Category categoryHousesSummerResidencesCottages = new Category("Дома, дачи, коттеджи", categoryProperty, categoryHousesSummerResidencesCottages3level);
        categoryProperty2level.add(categoryHousesSummerResidencesCottages);
        Category categoryLand = new Category("Земельные участки", categoryProperty, categoryLand3level);
        categoryProperty2level.add(categoryLand);
        Category categoryGaragesAndParkingSpaces = new Category("Гаражи и машиноместа", categoryProperty, categoryGaragesAndParkingSpaces3level);
        categoryProperty2level.add(categoryGaragesAndParkingSpaces);
        Category categoryCommercialProperty = new Category("Коммерческая недвижимость", categoryProperty, categoryCommercialProperty3level);
        categoryProperty2level.add(categoryCommercialProperty);
        Category categoryPropertyAbroad = new Category("Недвижимость за рубежом", categoryProperty, categoryPropertyAbroad3level);
        categoryProperty2level.add(categoryPropertyAbroad);

        //3 вкладка   Категории--> Недвижимость --> КуплюПродамСдамСниму

        categoryApartments3level.add(new Category("Продам", categoryApartments, emptySet));
        categoryRooms3level.add(new Category("Продам", categoryRooms, emptySet));
        categoryGaragesAndParkingSpaces3level.add(new Category("Продам", categoryHousesSummerResidencesCottages, emptySet));
        categoryLand3level.add(new Category("Продам", categoryLand, emptySet));
        categoryHousesSummerResidencesCottages3level.add(new Category("Продам", categoryGaragesAndParkingSpaces, emptySet));
        categoryCommercialProperty3level.add(new Category("Продам", categoryCommercialProperty, emptySet));
        categoryPropertyAbroad3level.add(new Category("Продам", categoryPropertyAbroad, emptySet));

        categoryApartments3level.add(new Category("Сдам", categoryApartments, emptySet));
        categoryRooms3level.add(new Category("Сдам", categoryRooms, emptySet));
        categoryGaragesAndParkingSpaces3level.add(new Category("Сдам", categoryHousesSummerResidencesCottages, emptySet));
        categoryLand3level.add(new Category("Сдам", categoryLand, emptySet));
        categoryHousesSummerResidencesCottages3level.add(new Category("Сдам", categoryGaragesAndParkingSpaces, emptySet));
        categoryCommercialProperty3level.add(new Category("Сдам", categoryCommercialProperty, emptySet));
        categoryPropertyAbroad3level.add(new Category("Сдам", categoryPropertyAbroad, emptySet));

        categoryApartments3level.add(new Category("Куплю", categoryApartments, emptySet));
        categoryRooms3level.add(new Category("Куплю", categoryRooms, emptySet));
        categoryGaragesAndParkingSpaces3level.add(new Category("Куплю", categoryHousesSummerResidencesCottages, emptySet));
        categoryLand3level.add(new Category("Куплю", categoryLand, emptySet));
        categoryHousesSummerResidencesCottages3level.add(new Category("Куплю", categoryGaragesAndParkingSpaces, emptySet));
        categoryCommercialProperty3level.add(new Category("Куплю", categoryCommercialProperty, emptySet));
        categoryPropertyAbroad3level.add(new Category("Куплю", categoryPropertyAbroad, emptySet));

        categoryApartments3level.add(new Category("Сниму", categoryApartments, emptySet));
        categoryRooms3level.add(new Category("Сниму", categoryRooms, emptySet));
        categoryGaragesAndParkingSpaces3level.add(new Category("Сниму", categoryHousesSummerResidencesCottages, emptySet));
        categoryLand3level.add(new Category("Сниму", categoryLand, emptySet));
        categoryHousesSummerResidencesCottages3level.add(new Category("Сниму", categoryGaragesAndParkingSpaces, emptySet));
        categoryCommercialProperty3level.add(new Category("Сниму", categoryCommercialProperty, emptySet));
        categoryPropertyAbroad3level.add(new Category("Сниму", categoryPropertyAbroad, emptySet));

        categoryService.addCategory(categoryHobbiesAndLeisure);
        categoryService.addCategory(categoryTransport);
        categoryService.addCategory(cateroryAnimals);
        categoryService.addCategory(categoryConsumerElectronics);
        categoryService.addCategory(categoryPersonalItems);
        categoryService.addCategory(categoryProperty);
        categoryService.addCategory(categoryJob);
        categoryService.addCategory(categoryServices);
        categoryService.addCategory(categoryForHomeAndGarden);
        categoryService.addCategory(categoryBusiness);
    }
}
