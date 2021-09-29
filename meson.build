project('v-c-bc-client', 'c', 'cpp',
  version : '0.4.3-snapshot',
  default_options: ['c_std=gnu11', 'cpp_std=c++14', 'buildtype=release'],
  meson_version : '>=0.59.0'
)

message('Using build type: ' + get_option('buildtype'))

force_velo_toolchain = get_option('force_velo_toolchain')

if force_velo_toolchain
  cc = meson.get_compiler('c')
  cc_version = run_command(cc.cmd_array() + ['--version'], check : true).stdout()
  if not cc_version.contains('(velo)')
    error('force_velo_toolchain is set and you arn\'t using the velo toolchain.')
  endif
  cpp = meson.get_compiler('cpp')
  cpp_version = run_command(cpp.cmd_array() + ['--version'], check : true).stdout()
  if not cpp_version.contains('(velo)')
    error('force_velo_toolchain is set and you arn\'t using the velo toolchain.')
  endif
else
  warning('Not using the blessed velo gcc toolchain. Here be dragons.')
endif

add_project_arguments('-Wall', '-Werror', '-Wextra', language : 'c')
add_project_arguments('-Wall', '-Werror', '-Wextra', language : 'cpp')

src = run_command('find', './src/main/native/src', '-name', '*.c', check : true).stdout().strip().split('\n')

vcblockchain = dependency(
    'vcblockchain',
    required : true,
    fallback : ['vcblockchain', 'vcblockchain_dep']
)

vcblockchain_sub = subproject('vcblockchain')
vcblockchain_include_directories = \
    vcblockchain_sub.get_variable('vcblockchain_include_directories')

jni_lib_deps = [vcblockchain]

jni_include = include_directories('src/main/native/include')
config_include = include_directories('.')

jni_lib = static_library('v-c-bc-client', src,
  dependencies : jni_lib_deps,
  include_directories: [vcblockchain_include_directories, jni_include, config_include],
)

jni_dep = declare_dependency(
  include_directories : [vcblockchain_include_directories, jni_include, config_include],
  dependencies : [vcblockchain]
)

conf_data = configuration_data()
conf_data.set('VERSION', meson.project_version())
configure_file(
  input : 'config.h.in',
  output : 'config.h',
  configuration : conf_data
)
