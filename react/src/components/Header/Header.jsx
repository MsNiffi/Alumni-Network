import React, { useState } from "react";
import { UserAuth } from "../../context/AuthContext";
import { FaBars } from "react-icons/fa";
import { Link } from "react-router-dom";
import { NavbarItems } from "../Header/NavbarItems";

const Header = () => {
    const { user } = UserAuth();
    const [easterEggClick, setEasterEggClick] = useState(0);
    const [sidebar, setSidebar] = useState(false);
    const navItems = NavbarItems();

    const toggleMenu = () => {
        setSidebar(!sidebar);
        // Easter egg
        let timer;
        clearTimeout(timer);
        setTimeout(resetClick, 2000);
        setEasterEggClick(easterEggClick + 1);
        if (easterEggClick > 6) {
            alert("418 I'm a teapot");
            setEasterEggClick(0);
        }
    };

    const resetClick = () => {
        setEasterEggClick(0);
    };

    return (
        <div className={`${user && "sticky"} top-0 z-50`}>
            <header
                className={`flex bg-secondary border-b-2 border-tertiary text-white h-16 justify-center text-center items-center font-alumni text-4xl ${
                    user && "justify-between"
                }`}
            >
                <h1 className="drop-shadow-md ml-12"> Alumni Network</h1>
                {user && (
                    <button
                        type="button"
                        className="ml-auto mr-12"
                        onClick={toggleMenu}
                    >
                        <FaBars />
                    </button>
                )}
            </header>
            <nav
                className={`absolute bg-black opacity-90 z-10 right-0 w-1/2 md:w-1/5 h-[90vh] flex justify-start ${
                    !sidebar && "hidden"
                }`}
            >
                <ul className="w-full">
                    {navItems.map((item, index) => {
                        return (
                            <li
                                key={index}
                                className="text-2xl p-4 hover:bg-white hover:text-black w-full"
                            >
                                <Link
                                    to={item.path}
                                    className="flex"
                                    onClick={toggleMenu}
                                >
                                    {item.icon}
                                    <span className="ml-4">{item.title}</span>
                                </Link>
                            </li>
                        );
                    })}
                </ul>
            </nav>
        </div>
    );
};

export default Header;
