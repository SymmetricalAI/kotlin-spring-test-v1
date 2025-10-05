# System Monitorowania Postępów Budowy Gwiazdy Śmierci

## Wprowadzenie

Witaj w imperialnym projekcie o kryptonimie "Stardust". Twoim zadaniem jest stworzenie zaawansowanego systemu do monitorowania i zarządzania personelem pracującym przy budowie najważniejszej broni w historii Imperium Galaktycznego – Gwieździe Śmierci. Aplikacja zapewni precyzyjny nadzór nad czasem pracy, efektywnością oraz dyscypliną załogi, a także umożliwi bieżące raportowanie postępów Wielkiemu Moffowi Tarkinowi.

## Opis projektu

Projekt "Stardust" to aplikacja stworzona w celu monitorowania czasu pracy oraz postępów w budowie Gwiazdy Śmierci. System ma na celu automatyzację procesów kontroli personelu, raportowanie nieprawidłowości oraz dostarczanie danych analitycznych dotyczących efektywności pracy.

### Technologie
* **Kotlin**
* **Spring Framework**
* **Apache Kafka**
* **MongoDB**
* **Docker** https://www.docker.com/products/docker-desktop/

* Zalecamy instalację pluginu w intelji Kotest, do uruchamiania testów

---

## Wymagania funkcjonalne

**Wszelkie zmiany powinny być wykonane na osobnym branchu, a pod koniec sesji kodowania otwary Pull Request.**

### 1. Rejestracja czasu pracy
* **Rejestracja rozpoczęcia i zakończenia pracy:** Każdy szeregowy pracownik musi mieć możliwość zarejestrowania momentu rozpoczęcia i zakończenia swojej zmiany.
* **Wymiar czasu pracy:** Standardowa zmiana robocza wynosi **15 godzin** dziennie.

### 2. System powiadomień i alertów ("Nadzorca")
System musi integrować się z imperialnym systemem "Nadzorca" i wysyłać powiadomienia w następujących przypadkach:
* **Wejście na teren budowy po godzinach:** Automatyczne powiadomienie, gdy pracownik pojawi się na terenie konstrukcji poza wyznaczonymi godzinami pracy.
* **Niestawienie się do pracy:** Alert w przypadku, gdy pracownik nie rozpocznie swojej zmiany.
* **Niewykonanie pełnej zmiany:** Powiadomienie, jeśli pracownik zakończy pracę przed upływem wymaganych 15 godzin.
* **Przekroczenie czasu pracy:** Powiadomienie, jeśli pracownik nie zakończy pracy po upływie wymaganych 15 godzin.

### 3. Zarządzanie personelem i "reedukacja"
* **Automatyczna selekcja do reedukacji:** Na koniec każdego miesiąca system musi automatycznie identyfikować pracowników, u których zanotowano "podejrzane zachowania" (np. notoryczne spóźnienia, niepełne zmiany). Tacy pracownicy są wycofywani z budowy, a informacja o tym fakcie jest przesyłana do systemu "Nadzorca" w celu podjęcia dalszych działań.
* **Ręczne zgłoszenie do reedukacji:** Przełożony brygady ma możliwość natychmiastowego zgłoszenia podwładnego do reedukacji. Zgłoszenie wymaga podania krótkiego, lakonicznego powodu.

### 4. Raportowanie i podgląd na żywo
* **Panel Wielkiego Moffa Tarkina:** Komandor Tarkin musi mieć dostęp do panelu (dashboard), który umożliwia podgląd na żywo następujących informacji:
    * Status obecności pracowników.
    * Alerty dotyczące naruszeń dyscypliny (wejścia po godzinach, nieobecności, niepełne zmiany).
    * Bieżący postęp w budowie Gwiazdy Śmierci.
* **Obliczanie postępów budowy:** Postęp jest obliczany na podstawie sumy godzin roboczych i indywidualnej efektywności każdego pracownika, budowa zostanie uznana za zakończoną kiedy postęp osiągnie 10 Miliardów.
    * **Współczynnik bazowy:** Jedna godzina pracy pracownika o 100% efektywności przekłada się na postęp budowy o wartości `0.4200`.
    * **Efektywność pracownika:** Efektywność jest wyrażona w procentach i ustalana przez brygadzistę (np. 50% efektywności = `0.2100` postępu na godzinę). Wartość ta może być dynamicznie zmieniana w czasie.

### 5. Dokumentacja API
* **Dokumentacja OpenAPI (Swagger):** Każdy udostępniony endpoint API musi posiadać kompletną i aktualną dokumentację w standardzie OpenAPI. Jest to wymaganie kluczowe dla **Biura Bezpieczeństwa Imperialnego (BBI)**, które pod nadzorem dyrektora Orsona Krennica, planuje zintegrować się z systemem w celu wykorzystania danych do własnych celów analitycznych i operacyjnych.

### 5. Program Resocjalizacyjny "Nowa Szansa"
Imperium w swojej łaskawości oferuje wrogom możliwość odkupienia win poprzez ciężką i uczciwą pracę przy naszym wielkim projekcie. Należy zaimplementować mechanizm importu potencjalnych kandydatów do pracy z galaktycznej bazy danych.

* **Implementacja importu kandydatów:** Stwórz mechanizm (np. endpoint lub zadanie cykliczne), który będzie pobierał dane postaci z publicznego holonetu dostępnego pod adresem `https://swapi.dev/api/people/`. Zaimportowani kandydaci powinni zostać dodani do systemu jako nowi pracownicy z domyślnym, niskim wskaźnikiem efektywności, gotowi do przydzielenia do brygad roboczych. (Użyj w tym celu Feigna lub RestTemplate)

### 6. Incydent "Czerwony Droid": Sabotaż i Wymagania Naprawcze
Ostatnie analizy logów systemowych oraz raporty z niestabilnych testów integracyjnych ujawniły celowe działanie sabotażowe. Rebeliancki agent, ukrywający się pod fałszywą tożsamością, wprowadził do kodu z pozoru niegroźne modyfikacje. Spowodowały one jednak kaskadowe błędy w testach oraz subtelne problemy z wydajnością. Konieczne jest przywrócenie systemowi pełnej sprawności operacyjnej.