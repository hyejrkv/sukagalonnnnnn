const hamburger = document.querySelector('.hamburger');
const navMenu = document.querySelector('.nav-menu');
const navLinks = document.querySelectorAll('.nav-link');
const exploreBtn = document.querySelector('.explore-btn');
const mascot = document.querySelector('.mascot');
const clouds = document.querySelectorAll('.cloud');

// Helper function to debounce events
const debounce = (func, wait = 20) => {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
};

// Fade-in effect on load
document.addEventListener('DOMContentLoaded', () => {
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 0.5s ease';
        document.body.style.opacity = '1';
    }, 100);
});

// Hamburger menu toggle
hamburger?.addEventListener('click', () => {
    hamburger.classList.toggle('active');
    navMenu?.classList.toggle('active');
});

// Close menu on nav link click
navLinks.forEach(link => {
    link.addEventListener('click', (e) => {
        hamburger.classList.remove('active');
        navMenu.classList.remove('active');
        e.preventDefault();
        const targetId = link.getAttribute('href');
        if (targetId && targetId !== '#') {
            const targetSection = document.querySelector(targetId);
            targetSection?.scrollIntoView({ behavior: 'smooth' });
        }
    });
});

// Button click effect
exploreBtn?.addEventListener('click', () => {
    exploreBtn.style.transform = 'scale(0.95)';
    setTimeout(() => {
        exploreBtn.style.transform = 'scale(1)';
    }, 200);
});

if (mascot) {
    const handleMouseMove = (e) => {
        const { clientX, clientY } = e;
        const xPos = (window.innerWidth / 2 - clientX) / 30;
        const yPos = (window.innerHeight / 2 - clientY) / 30;
        mascot.style.transform = `translate(${xPos}px, ${yPos}px)`;
    };

    document.addEventListener('mousemove', debounce(handleMouseMove));
}

const observerOptions = { threshold: 0.1 };
const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('show');
        }
    });
}, observerOptions);

document.querySelectorAll('.hero-content, .mascot-container').forEach(el => {
    observer.observe(el);
});

const handleScroll = () => {
    const scrolled = window.pageYOffset;
    clouds.forEach((cloud, index) => {
        const speed = (index + 1) * 0.2;
        cloud.style.transform = `translateY(${scrolled * speed}px)`;
    });
};

if (clouds.length) {
    window.addEventListener('scroll', debounce(handleScroll));
}

window.addEventListener('load', () => {
    const loader = document.createElement('div');
    loader.className = 'preloader';
    document.body.appendChild(loader);

    setTimeout(() => {
        loader.style.opacity = '0';
        setTimeout(() => {
            loader.remove();
        }, 500);
    }, 1000);
});

// Reset menu on resize
window.addEventListener('resize', debounce(() => {
    if (window.innerWidth > 768) {
        hamburger?.classList.remove('active');
        navMenu?.classList.remove('active');
    }
}, 100));

// Button hover transition
const buttons = document.querySelectorAll('button');
buttons.forEach(button => {
    button.addEventListener('mouseover', () => {
        button.style.transition = 'all 0.3s ease';
    });
});