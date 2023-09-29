# We move the test files into the testing directory
file(COPY "${CMAKE_CURRENT_SOURCE_DIR}/hypervolume_test_data/" DESTINATION "${CMAKE_BINARY_DIR}/tests/hypervolume_test_data/")

function(ADD_PAGMO_TESTCASE arg1)
    if(PAGMO_TEST_NSPLIT)
        math(EXPR __PAGMO_TEST_NUM "(${_PAGMO_TEST_NUM} + 1) % ${PAGMO_TEST_NSPLIT}")
        set(_PAGMO_TEST_NUM ${__PAGMO_TEST_NUM} PARENT_SCOPE)
    endif()
    if(PAGMO_TEST_NSPLIT AND "${PAGMO_TEST_SPLIT_NUM}" STREQUAL "${_PAGMO_TEST_NUM}")
        return()
    endif()
    add_executable(${arg1} ${arg1}.cpp)
    target_link_libraries(${arg1} PRIVATE pagmo PRIVATE Boost::unit_test_framework)
    target_compile_options(${arg1} PRIVATE
        "$<$<CONFIG:Debug>:${PAGMO_CXX_FLAGS_DEBUG}>"
        "$<$<CONFIG:Release>:${PAGMO_CXX_FLAGS_RELEASE}>"
        "$<$<CONFIG:RelWithDebInfo>:${PAGMO_CXX_FLAGS_RELEASE}>"
        "$<$<CONFIG:MinSizeRel>:${PAGMO_CXX_FLAGS_RELEASE}>"
    )
    # Set the minimum C++ standard to C++17
    target_compile_features(${arg1} PRIVATE cxx_std_17)
    set_property(TARGET ${arg1} PROPERTY CXX_EXTENSIONS NO)
    target_compile_definitions(${arg1} PRIVATE BOOST_ALLOW_DEPRECATED_HEADERS)
    add_test(NAME ${arg1} COMMAND ${arg1})
endfunction()

# Tests requiring no dependencies (in alphabetical order)
ADD_PAGMO_TESTCASE(schwefel)

if(PAGMO_WITH_EIGEN3)
    ADD_PAGMO_TESTCASE(cmaes)
    ADD_PAGMO_TESTCASE(eigen3_serialization)
    ADD_PAGMO_TESTCASE(xnes)
endif()

if(PAGMO_WITH_NLOPT)
    ADD_PAGMO_TESTCASE(nlopt)
endif()

if(PAGMO_WITH_IPOPT)
    ADD_PAGMO_TESTCASE(ipopt)
endif()

if (PAGMO_WITH_FORK_ISLAND)
    ADD_PAGMO_TESTCASE(fork_island)
endif()
