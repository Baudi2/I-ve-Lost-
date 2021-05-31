package ru.startandroid.develop.testprojectnavigation.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.Gravity
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.drawerlayout.widget.DrawerLayout
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.HeaderItem

//? Чтобы скрыть клавиатуру после нажатия кнопки
fun hideKeyboard(view: View) {
    try {
        val imn =
            APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

//? получаем как аргмент сообщение которое надо показать и выводим с ним тост
fun shortToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

//? тоже самое только разная длительность
fun longToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
}

//? метод в который мы передаем id строки которую нужно получить и он возвращает эту строку
//? из resources.values.strings
fun stringGet(id: Int) = APP_ACTIVITY.resources.getString(id)

//? копирует текст из textView и сохраняет в буфер, i.e. clipboard
fun copyText(textView: TextView) {
    val manager = APP_ACTIVITY.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", textView.text)
    manager.setPrimaryClip(clipData)
}

//? функция вызова popupMenu, используется в нашёл, потерял и фрагменте сообщений
fun explainArgument() {explainArgumentsPopup()} //!.
@SuppressLint("RestrictedApi")
fun showPopup(
    view: View,
    menu: Int,
    message: String,
    secondMessage: String,
    menuItemId: Int,
    secondMenuItemId: Int,
    textView: TextView?
) {
    val menuBuilder = MenuBuilder(APP_ACTIVITY)
    val menuInflater = MenuInflater(APP_ACTIVITY)
    menuInflater.inflate(menu, menuBuilder)
    //? menuHelper нужен для того чтобы показывать иконку т.к. по дефолту не видны
    val optionsMenu = MenuPopupHelper(APP_ACTIVITY, menuBuilder, view)
    optionsMenu.setForceShowIcon(true)
    //? если textView не равен нулю значит мы в фрагменте сообщений, а там нужен аттрибут гравитации
    if (textView != null) optionsMenu.gravity = Gravity.END


    //? слушатель нажатий на отдельный элемент меню
    menuBuilder.setCallback((object : MenuBuilder.Callback {
        override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
            //? определяем что произойдет по нажатию также нужно вернуть true
            return when (item.itemId) {
                menuItemId -> {
                    shortToast(message)
                    true
                }
                secondMenuItemId -> {
                    //? показываем это меню если переданный textView неравен null
                    //? что означает что мы находимся в фрагменте сообщений
                    shortToast(secondMessage)
                    if (textView != null) {
                        copyText(textView)
                    }
                    true
                }
                else -> false
            }
        }

        override fun onMenuModeChange(menu: MenuBuilder) {}
    }))
    //? показываем созданное popupMenu
    optionsMenu.show()
}

//? получаем доступ через активити к drawerLayout и у него устанавливаем блок на
//? вытягиваем справа drawerLayout
fun lockDrawer() {
    APP_ACTIVITY.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
}

//? убираем блок установленный ранее
fun unlockDrawer() {
    APP_ACTIVITY.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
}

//? убираем через toolbar иконку бургер там где она нам не нужна
fun hideDrawer() {
    APP_ACTIVITY.toolbar.navigationIcon = null
}

//? через активити получаем доступ к самому выдвижному меню, далее получаем доступ к
//? элементам его меню, затем находим нужный элемент этого меню и вызываем его метод
//? setVisibility (поставить видимость) и уставливаем видимость на невидимый
fun hideHome() {
    APP_ACTIVITY.navView.menu.findItem(R.id.home_drawer).isVisible = false
}

//? возвращаем видимость этого элемента обратно
fun showHome() {
    APP_ACTIVITY.navView.menu.findItem(R.id.home_drawer).isVisible = true
}

fun selectedListOfTypes(type: Int): ArrayList<HeaderItem> {
    // the we create new empty arrayList<>
    val list = ArrayList<HeaderItem>()

    // and it uses the size value in the for loop to fill this list with data
    // Note: this is a custom algorithm that has nothing to do neither with android nor recyclerView
    var displayType: String
    // this part is only responsible for alternating between our 5 drawables
        if (type == 1) {
            for (i in 0..6) {
                displayType = when (i) {
                    0 -> "Кот"
                    1 -> "Собака"
                    2 -> "Корова"
                    3 -> "Овца"
                    4 -> "Попугай"
                    5 -> "Хомяк"
                    else -> "Енот"
                }
                val item = HeaderItem(displayType, null)
                list += item
            }
        }

        if (type == 2) {
            for (i in 0..7) {
                displayType = when (i) {
                    0 -> "Паспорт"
                    1 -> "Водительские права"
                    2 -> "Свидетельство о рождении"
                    3 -> "ИНН"
                    4 -> "Страховой полис"
                    5 -> "Домовая книга"
                    6 -> "СНИЛС"
                    else -> "Загран паспорт"
                }
                val item = HeaderItem(displayType, null)
                list += item
            }
        }

        if (type == 3) {
            for (i in 0..11) {
                displayType = when (i) {
                    0 -> "Кольцо"
                    1 -> "Часы"
                    2 -> "Браслет"
                    3 -> "Ожерелье"
                    4 -> "Серьги"
                    5 -> "Цепочка"
                    6 -> "Украшение для волос"
                    7 -> "Бусы"
                    8 -> "Подвеска, кулон"
                    9 -> "Подвеска, кулон"
                    10 -> "Брошь"
                    else -> "Корона"
                }
                val item = HeaderItem(displayType, null)
                list += item
            }
        }

        if (type == 4) {
            for (i in 0..26) {
                displayType = when (i) {
                    0 -> "Отец"
                    1 -> "Мать"
                    2 -> "Бабушка"
                    3 -> "Дедушка"
                    4 -> "Прадедушка"
                    5 -> "Прабабушка"
                    6 -> "Сын"
                    7 -> "Дочь"
                    8 -> "Внук"
                    9 -> "Внучка"
                    10 -> "Сестра"
                    11 -> "Брат"
                    12 -> "Тётя"
                    13 -> "Дядя"
                    14 -> "Племянник"
                    15 -> "Племянница"
                    16 -> "Двоюродный брат"
                    17 -> "Двоюродная сестра"
                    18 -> "Троюродный брат"
                    19 -> "Троюродная сестра"
                    20 -> "Жена"
                    21 -> "Муж"
                    22 -> "Сноха"
                    23 -> "Друг"
                    24 -> "Знакомый"
                    else -> "Зять"
                }
                val item = HeaderItem(displayType, null)
                list += item
            }
        }

        if (type == 5) {
            for (i in 0..11) {
                displayType = when (i) {
                    0 -> "от 500 до 1000"
                    1 -> "от 1000 до 3000"
                    2 -> "от 3000 до 7000"
                    3 -> "от 7000 до 10000"
                    4 -> "от 10000 до 15000"
                    5 -> "от 15000 до 20000"
                    6 -> "от 20000 до 25000"
                    7 -> "от 25000 до 30000"
                    8 -> "от 30000 до 35000"
                    9 -> "от 35000 до 40000"
                    10 -> "от 40000 до 45000"
                    else -> "от 50000 и выше"
                }
                val item = HeaderItem(displayType, null)
                list += item
            }
        }
    // after filling the list with data we eventually return it
    return list
}

fun monthToString(month: Int): String {
    return when(month) {
        0 -> "январь"
        1 -> "февраль"
        2 -> "март"
        3 -> "апрель"
        4 -> "май"
        5 -> "июнь"
        6 -> "июль"
        7 -> "август"
        8 -> "сентябрь"
        9 -> "октябрь"
        10 -> "ноябрь"
        else -> "декабрь"
    }
}


fun firebaseEnglishExceptionToRussian(exception: String?) {
    if (exception == null) return
    if (exception.contains("The email address is badly formatted.")) {
        longToast("Неудалось создать аккаунт: Введен неправильный формат электронной почты")
    }
    if (exception.contains("The given password is invalid. [ Password should be at least 6 characters ]")) {
        longToast("Неудалось создать аккаунт: Введен не верный пароль [ Пароль должен состоять как минимум из шести символов ]")
    }
    if(exception.contains("The email address is already in use by another account.")) {
        longToast("Неудалось создать аккаунт: Аккаунт с такой электронной почтой уже существует")
    }
    if (exception.contains("A network error (such as timeout, interrupted connection or unreachable host) has occurred")) {
        longToast("Неудалось создать аккаунт: Произошла сетевая ошибка (например, тайм-аут, прерванное соединение или недоступный хост)")
    }
}


















