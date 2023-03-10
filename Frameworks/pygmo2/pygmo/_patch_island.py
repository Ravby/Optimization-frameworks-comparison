# Copyright 2020, 2021 PaGMO development team
#
# This file is part of the pygmo library.
#
# This Source Code Form is subject to the terms of the Mozilla
# Public License v. 2.0. If a copy of the MPL was not distributed
# with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

from .core import island


def _island_extract(self, t):
    """Extract the user-defined island.

    This method allows to extract a reference to the user-defined island (UDI) stored within this
    :class:`~pygmo.island` instance. The behaviour of this function depends on the value
    of *t* (which must be a :class:`type`) and on the type of the internal UDI:

    * if the type of the UDI is *t*, then a reference to the UDI will be returned
      (this mirrors the behaviour of the corresponding C++ method
      :cpp:func:`pagmo::island::extract()`),
    * if *t* is :class:`object` and the UDI is a Python object (as opposed to an
      :ref:`exposed C++ island <islands_cpp>`), then a reference to the
      UDI will be returned (this allows to extract a Python UDI without knowing its type),
    * otherwise, :data:`None` will be returned.

    Args:
        t (:class:`type`): the type of the user-defined island to extract

    Returns:
        a reference to the internal user-defined island, or :data:`None` if the extraction fails

    Raises:
        TypeError: if *t* is not a :class:`type`

    Examples:
        >>> import pygmo as pg
        >>> i1 = pg.island(algo=pg.de(), prob=pg.rosenbrock(), size=20, udi=pg.thread_island())
        >>> i1.extract(pg.thread_island) # doctest: +SKIP
        <pygmo.core.thread_island at 0x7f8e478e1210>
        >>> i1.extract(pg.mp_island) is None
        True
        >>> class isl:
        ...     def run_evolve(self, algo, pop):
        ...         return algo, pop
        >>> i2 = pg.island(algo=pg.de(), prob=pg.rosenbrock(), size=20, udi=isl())
        >>> i2.extract(object) # doctest: +SKIP
        <__main__.isl at 0x7f8e478261d0>
        >>> i2.extract(isl) # doctest: +SKIP
        <__main__.isl at 0x7f8e478261d0>
        >>> i2.extract(pg.thread_island) is None
        True

    """
    if not isinstance(t, type):
        raise TypeError("the 't' parameter must be a type")
    if hasattr(t, "_pygmo_cpp_island"):
        return self._cpp_extract(t())
    return self._py_extract(t)


def _island_is(self, t):
    """Check the type of the user-defined island.

    This method returns :data:`False` if :func:`extract(t) <pygmo.island.extract>` returns
    :data:`None`, and :data:`True` otherwise.

    Args:
        t (:class:`type`): the type that will be compared to the type of the UDI

    Returns:
        bool: whether the UDI is of type *t* or not

    Raises:
        unspecified: any exception thrown by :func:`~pygmo.island.extract()`

    """
    return not self.extract(t) is None


# Do the actual patching.
setattr(island, "extract", _island_extract)
setattr(island, "is_", _island_is)
