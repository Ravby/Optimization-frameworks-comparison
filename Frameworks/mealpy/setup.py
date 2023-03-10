#!/usr/bin/env python
# Created by "Thieu" at 13:24, 27/02/2022 ----------%
#       Email: nguyenthieu2102@gmail.com            %
#       Github: https://github.com/thieu1995        %
# --------------------------------------------------%

from setuptools import setup, find_packages


def readme():
    with open('README.md', encoding='utf-8') as f:
        README = f.read()
    return README


setup(
    name="mealpy",
    version="2.5.1",
    author="Thieu",
    author_email="nguyenthieu2102@gmail.com",
    description="MEALPY: A Framework Of The State-Of-The-Art Meta-Heuristic Algorithms In Python",
    long_description=readme(),
    long_description_content_type="text/markdown",
    url="https://github.com/thieu1995/mealpy",
    download_url="https://github.com/thieu1995/mealpy/archive/v2.5.1.zip",
    packages=find_packages(exclude=['*tests', 'examples*']),
    include_package_data=True,
    classifiers=[
        "Development Status :: 5 - Production/Stable",
        "Intended Audience :: Developers",
        "Intended Audience :: Education",
        "Intended Audience :: Information Technology",
        "Intended Audience :: Science/Research",
        "License :: OSI Approved :: GNU General Public License v3 (GPLv3)",
        "Natural Language :: English",
        "Programming Language :: Python :: 3",
        "Programming Language :: Python :: 3.7",
        "Programming Language :: Python :: 3.8",
        "Programming Language :: Python :: 3.9",
        "Programming Language :: Python :: 3.10",
        "Topic :: System :: Benchmark",
        "Topic :: Scientific/Engineering",
        "Topic :: Scientific/Engineering :: Mathematics",
        "Topic :: Scientific/Engineering :: Artificial Intelligence",
        "Topic :: Scientific/Engineering :: Information Analysis",    
        "Topic :: Scientific/Engineering :: Visualization",
        "Topic :: Scientific/Engineering :: Bio-Informatics",
        "Topic :: Software Development :: Build Tools",
        "Topic :: Software Development :: Libraries",
        "Topic :: Software Development :: Libraries :: Python Modules",
        "Topic :: Utilities",
    ],

    install_requires=["numpy>=1.16.5", "matplotlib>=3.1.3", "scipy>=1.7.1", "pandas>=1.2.0", "opfunu>=1.0.0"],
    python_requires='>=3.7',
)
